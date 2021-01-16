/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.dao.*;
import com.facesite.modules.game.xiao.entity.*;
import com.facesite.modules.game.xiao.utils.BaseGameContact;
import com.facesite.modules.game.xiao.utils.DbGameContact;
import com.facesite.modules.game.xiao.utils.HttpGameContact;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.service.system.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户信息Service
 * @author nada
 * @version 2021-01-11
 */
@Service
@Transactional(readOnly=true)
public class HgameUserInfoApiService extends CrudService<HgameUserInfoDao, HgameUserInfo> {

	@Autowired
	HgameUserInfoDao hgameUserInfoDao;
	@Autowired
	HgameUserRefDao hgameUserRefDao;
	@Autowired
	HgamePlayRecordDao hgamePlayRecordDao;
	@Autowired
	HgameInfoDao hgameInfoDao;
	@Autowired
	HgamePlayLogDao hgamePlayLogDao;

	/**
	 * @desc 使用道具
	 * @author nada
	 * @create 2021/1/16 6:01 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject spendBooster(GameData gameData) {
		try {
			Long bootserId = BaseGameContact.getLong(gameData.getBoosterId());
			Long level = BaseGameContact.getLong(gameData.getLevel()) + 1;
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			if(StringUtils.isAnyEmpty(userId,gameId)){
				return BaseGameContact.failed("paramers is empty");
			}
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(userId,gameId));
				if(oldGameUserRef == null || oldGameUserRef.getHgameUserInfo() == null){
					return BaseGameContact.failed("get user game info failed");
				}
				String olBboostersCount = oldGameUserRef.getBoostersCount();
				if(StringUtils.isEmpty(olBboostersCount)){
					return BaseGameContact.failed("get game booters gold failed");
				}
				//更新用户游戏信息
				Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.spendGameUserRefboosters(userId,gameId,bootserId,olBboostersCount));
				if(BaseGameContact.isOkDb(dbIndex)){
					Long gole = 0L;
					Long score = 0L;
					String remarks = "使用道具:"+(bootserId+1);
					hgamePlayLogDao.insert(DbGameContact.saveLog(DbGameContact.LOG_TYPE_3,userId,gameId,level,gole,score,bootserId+1,remarks));
					return BaseGameContact.success(true);
				}
				return BaseGameContact.failed("Save game level up log failed");
			}
		} catch (Exception e) {
			logger.error("游戏升级记录异常",e);
			return BaseGameContact.failed("Save game level up log error");
		}
	}

	/**
	 * @desc 购买道具
	 * @author nada
	 * @create 2021/1/16 4:38 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject addBooster(GameData gameData) {
		try {
			Long bootserId = BaseGameContact.getLong(gameData.getBoosterId());
			Long needGold = BaseGameContact.getLong(gameData.getNeedGold());
			Long level = BaseGameContact.getLong(gameData.getLevel()) + 1;
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			if(StringUtils.isAnyEmpty(userId,gameId)){
				return BaseGameContact.failed("paramers is empty");
			}
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(userId,gameId));
				if(oldGameUserRef == null || oldGameUserRef.getHgameUserInfo() == null){
					return BaseGameContact.failed("get user game info failed");
				}
				Long oldGold = oldGameUserRef.getGold();
				if(needGold < 1 || needGold > oldGold){
					return BaseGameContact.failed("user gold not enough");
				}
				HgameInfo hgameInfo = oldGameUserRef.getHgameInfo();
				if(hgameInfo == null){
					return BaseGameContact.failed("get game info failed");
				}
				String bootersGolds = hgameInfo.getBoostersGold();
				if(StringUtils.isEmpty(bootersGolds)){
					return BaseGameContact.failed("get game booters gold failed");
				}
				JSONArray array = JSONArray.parseArray(bootersGolds);
				Long value = array.getLongValue(bootserId.intValue());
				if(!needGold.equals(value)){
					return BaseGameContact.failed("update game booters gold failed");
				}
				String olBboostersCount = oldGameUserRef.getBoostersCount();
				Integer userType = oldGameUserRef.getHgameUserInfo().getType();
				String token = oldGameUserRef.getHgameUserInfo().getToken();

				//app和本地乐豆同步
				String tag = "购买道具:"+bootserId;
				Boolean isSync = this.syncAppGold(level,userType,token,userId,gameId,-needGold,0L,tag);
				if(!isSync){
					logger.error("app和本地乐豆同步失败:{}",isSync);
					return BaseGameContact.failed("update app user gold failed");
				}

				//更新用户游戏信息
				Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.addGameUserRefboosters(userId,gameId,bootserId,olBboostersCount,isSync));
				if(BaseGameContact.isOkDb(dbIndex)){
					Long gole = -needGold;
					Long score = 0L;
					String remarks = "购买道具:"+(bootserId+1);
					hgamePlayLogDao.insert(DbGameContact.saveLog(DbGameContact.LOG_TYPE_3,userId,gameId,level,gole,score,bootserId+1,remarks));
					return BaseGameContact.success(true);
				}
				return BaseGameContact.failed("Save game level up log failed");
			}
		} catch (Exception e) {
			logger.error("游戏升级记录异常",e);
			return BaseGameContact.failed("Save game level up log error");
		}
	}


	/**
	 * @desc 游戏升级记录
	 * @author nada
	 * @create 2021/1/14 7:37 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject updateGamelevelUp(GameData gameData) {
		try {
			Long start = BaseGameContact.getLong(gameData.getStart());
			Long gold = BaseGameContact.getLong(gameData.getGold());
			Long score = BaseGameContact.getLong(gameData.getScore());
			Long level = BaseGameContact.getLong(gameData.getLevel()) + 1;
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			String playId = gameData.getPlayId();
			if(StringUtils.isAnyEmpty(userId,gameId)){
				return BaseGameContact.failed("paramers is empty");
			}
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(userId,gameId));
				if(oldGameUserRef == null || oldGameUserRef.getHgameUserInfo() == null){
					return BaseGameContact.failed("get user game info failed");
				}
				//战绩重置
				Integer type = DbGameContact.PLAY_TYPE_3;
				String oldStarsPerLevel = oldGameUserRef.getStarsPerLevel();
				Integer userType = oldGameUserRef.getHgameUserInfo().getType();
				String token = oldGameUserRef.getHgameUserInfo().getToken();
				List<HgamePlayRecord>  list = hgamePlayRecordDao.findList(DbGameContact.paramsGamePlayRecord(type,userId,gameId,level));
				if(list !=null && list.size() > 0){
					HgamePlayRecord oldRecord = list.get(0);
					Boolean isOk = this.updateBestGameRecord(oldRecord,userType,token,level,gold,score,start,oldStarsPerLevel);
					logger.info("修改战绩最好的游戏战局:{}",isOk);
					return BaseGameContact.success(isOk);
				}

				//初始化战局
				String tag = "闯"+level+"关结算:"+gold;
				HgamePlayRecord newRecord = DbGameContact.initGamePlayRecord(userId,gameId,playId,type,level,gold,score,start,tag);
				Long dbIndex = hgamePlayRecordDao.insert(newRecord);
				if(!BaseGameContact.isOkDb(dbIndex)){
					return BaseGameContact.failed("save init game record failed");
				}

				//app和本地乐豆同步
				Boolean isSync = this.syncAppGold(level,userType,token,userId,gameId,gold,score,tag);
				if(!isSync){
					logger.error("app和本地乐豆同步失败:{}",isSync);
					return BaseGameContact.failed("update app user gold failed");
				}

				//更新用户游戏信息
				dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRef(userId,gameId,level,score,start,oldStarsPerLevel,isSync));
				if(BaseGameContact.isOkDb(dbIndex)){
					String remarks = "闯关升级:"+gold;
					hgamePlayLogDao.insert(DbGameContact.saveLog(DbGameContact.LOG_TYPE_2,userId,gameId,level,gold,score,0L,remarks));
					return BaseGameContact.success(true);
				}
				return BaseGameContact.failed("Save game level up log failed");
			}
		} catch (Exception e) {
			logger.error("游戏升级记录异常",e);
			return BaseGameContact.failed("Save game level up log error");
		}
	}

	/**
	 * @desc 修改战绩最好的游戏战局
	 * @author nada
	 * @create 2021/1/15 9:42 上午
	*/
	@Transactional(readOnly=false)
	public Boolean updateBestGameRecord(HgamePlayRecord oldRecord,Integer userType,String token,Long level, Long gold, Long score,Long start,String oldStarsPerLevel) {
		try {
			String userId = oldRecord.getUserId();
			String gameId = oldRecord.getGameId();
			Long oldGold = BaseGameContact.getLong(oldRecord.getGold());
			Long oldScore = BaseGameContact.getLong(oldRecord.getScore());
			Long oldLevel = BaseGameContact.getLong(oldRecord.getLevel());
			Long oldStart = BaseGameContact.getLong(oldRecord.getStart());
			if(!(level).equals(oldLevel)){
				return false;
			}
			if(StringUtils.isAnyEmpty(userId,gameId)){
				return false;
			}
			Boolean isSync = false;
			Boolean isUpdate = false;
			Long newScore = 0L;
			if(score > oldScore){
				isUpdate = true;
				oldRecord.setScore(score);
				newScore = score - oldScore;
			}
			if(start > oldStart){
				isUpdate = true;
				oldRecord.setStart(start);
			}
			if(gold > oldGold){
				isUpdate = true;
				oldRecord.setGold(gold);
				Long newGold = gold - oldGold;
				String tag = "重玩"+level+"关结算:"+newGold;
				isSync = this.syncAppGold(level,userType,token,userId,gameId,newGold,newScore,tag);
				if(!isSync){
					logger.error("app和本地乐豆同步失败:{}",isSync);
					return false;
				}
				String remarks = "重新闯关:"+newGold;
				hgamePlayLogDao.insert(DbGameContact.saveLog(DbGameContact.LOG_TYPE_2,userId,gameId,level,newGold,newScore,0L,remarks));
			}
			if(!isUpdate){
				return false;
			}
			Long dbIndex = hgamePlayRecordDao.update(oldRecord);
			Boolean isOk = BaseGameContact.isOkDb(dbIndex);
			if(!isOk){
				return false;
			}
			//更新用户游戏信息
			dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRef(userId,gameId,level,newScore,start,oldStarsPerLevel,isSync));
			if(!BaseGameContact.isOkDb(dbIndex)){
				logger.error("更新用户游戏信息失败:{}",dbIndex);
				return false;
			}
			return BaseGameContact.isOkDb(dbIndex);
		} catch (Exception e) {
			logger.error("修改战绩最好的游戏战局异常",e);
			return false;
		}
	}

	/**
	 * @desc app和本地乐豆同步
	 * @author nada
	 * @create 2021/1/15 10:05 上午
	 */
	@Transactional(readOnly=false)
	public Boolean syncAppGold(Long level,Integer userType,String token,String userId,String gameId, Long gold,Long socre,String tag) {
		if(userType != null && userType ==2){
			JSONObject result = HttpGameContact.updateAppGold(token,gold,tag);
			Boolean isOk = BaseGameContact.isOk(result);
			String remarks = "";
			if(!isOk){
				logger.error("app和本地乐豆同步失败:{}",result);
				remarks = "同步呵豆失败:"+gold;
			}else{
				remarks = "同步呵豆成功:"+gold;
			}
			hgamePlayLogDao.insert(DbGameContact.saveLog(DbGameContact.LOG_TYPE_1,userId,gameId,level,gold,socre,0L,remarks));
		}
		Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRefGold(userId,gameId,gold));
		if(BaseGameContact.isOkDb(dbIndex)){
			return true;
		}
		return false;
	}

	/***
	 * @desc获取用户信息
	 * @author nada
	 * @create 2021/1/12 5:51 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject getUserInfo(GameData gameData) {
		try {
			String ip = gameData.getIpAddress();
			String uid = gameData.getUid();
			if(StringUtils.isEmpty(uid)){
				return BaseGameContact.failed("token parameter is empty");
			}
			List<HgameInfo> hgameInfoList = hgameInfoDao.findList(DbGameContact.paramsGameInfo(2));
			if(hgameInfoList == null || hgameInfoList.size() < 1){
				return BaseGameContact.failed("get gameinfo failed");
			}
			HgameInfo hgameInfo = hgameInfoList.get(0);
			HgameUserRef hgameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(uid));
			if(hgameUserRef != null){
				JSONObject response = DbGameContact.responseGameUserInfo(hgameInfo,hgameUserRef);
				logger.info("获取用户信息:{}",response);
				return BaseGameContact.success(response);
			}
			//游客玩家
			HgameUserInfo hgameUserInfo = hgameUserInfoDao.getByEntity(DbGameContact.paramsGameUserId(uid));
			if (hgameUserInfo == null || StringUtils.isEmpty(hgameUserInfo.getId())) {
				Long seqId = hgameUserInfoDao.getNextSequence();
				HgameUserInfo initUserInfo = DbGameContact.initVisitorUserInfo(seqId, ip,uid);
				hgameUserInfoDao.addSequence();
				hgameUserRef = this.initGameDb(initUserInfo, hgameInfo);
			}
			JSONObject response = DbGameContact.responseGameUserInfo(hgameInfo,hgameUserRef);
			logger.info("获取用户信息:{}",response);
			return BaseGameContact.success(response);
		} catch (Exception e) {
			Console.log("获取用户信息异常",e);
			return BaseGameContact.failed("get userinfo error");
		}
	}

	/**
	 * @desc app地址初始化
	 * @author nada
	 * @create 2021/1/16 9:19 下午
	*/
	@Transactional(readOnly=false)
	public GameData initUid(String token,String ip) {
		try {
			List<HgameInfo> hgameInfoList = hgameInfoDao.findList(DbGameContact.paramsGameInfo(2));
			if(hgameInfoList == null || hgameInfoList.size() < 1){
				return null;
			}
			HgameInfo hgameInfo = hgameInfoList.get(0);

			GameData gameData = new GameData();
			gameData.setUrl(hgameInfo.getUrl());
			if(StringUtils.isEmpty(token)){
				return gameData;
			}
			String userId = this.syncGetAppUserInfo(ip,token,hgameInfo);
			if(StringUtils.isNotEmpty(userId)){
				hgameUserInfoDao.update(DbGameContact.paramsGameUserInfoUpdate(userId,token));
			}
			gameData.setUid(userId);
			return gameData;
		} catch (Exception e) {
			Console.log("获取用户信息异常",e);
			return null;
		}
	}

	/**
	 * @desc 同步获取app玩家信息
	 * @author nada
	 * @create 2021/1/15 11:24 上午
	*/
	@Transactional(readOnly=false)
	public String syncGetAppUserInfo(String ip,String token,HgameInfo hgameInfo) {
		try {
			synchronized (token) {
				JSONObject result = HttpGameContact.getUserInfo(token);
				Boolean isOk = BaseGameContact.isOk(result);
				if (!isOk) {
					return "";
				}
				//会员玩家
				JSONObject resData = result.getJSONObject("result");
				String parentId = resData.getString("userInfo_ID");
				HgameUserInfo hgameUserInfo = hgameUserInfoDao.getByEntity(DbGameContact.paramsGameUserInfo(parentId));
				if (hgameUserInfo != null && StringUtils.isNotEmpty(hgameUserInfo.getId())) {
					return hgameUserInfo.getId();
				}
				HgameUserInfo initUserInfo = DbGameContact.initMemberUserInfo(token, resData, DbGameContact.TYPE_MEMBER);
				HgameUserRef hgameUserRef = this.initGameDb(initUserInfo, hgameInfo);
				return hgameUserRef.getUserId();
			}
		} catch (Exception e) {
			logger.error("同步获取app玩家信息异常", e);
			return null;
		}
	}

	/**
	 * @desc 会员玩家初始化游戏
	 * @author nada
	 * @create 2021/1/12 7:25 下午
	*/
	@Transactional(readOnly=false)
	public HgameUserRef initGameDb(HgameUserInfo initUserInfo,HgameInfo hgameInfo){
		try {
			long db1 = hgameUserInfoDao.insert(initUserInfo);
			if(!BaseGameContact.isOkDb(db1)){
				return null;
			}
			String userId = initUserInfo.getId();
			HgameUserRef hgameUserRef = DbGameContact.initGameUserRef(hgameInfo,userId);
			long db2 = hgameUserRefDao.insert(hgameUserRef);
			if(!BaseGameContact.isOkDb(db2)){
				return null;
			}
			return hgameUserRef;
		} catch (Exception e) {
			Console.log("初始化游戏异常",e);
			return null;
		}
	}

	/***
	 * @desc获取用户信息
	 * @author nada
	 * @create 2021/1/12 5:51 下午
	 */
	public JSONObject gameOver(JSONObject reqData) {
		JSONObject result = new JSONObject();
		return result;
	}

	/***
	 * @desc获取用户信息
	 * @author nada
	 * @create 2021/1/12 5:51 下午
	 */
	public JSONObject gameStart(JSONObject reqData) {
		JSONObject result = new JSONObject();
		return result;
	}

}
