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
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
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
	@Autowired
	HgameUserInfoHelpService helpService;

	/**
	 * @desc 使用道具
	 * @author nada
	 * @create 2021/1/16 6:01 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject spendBooster(GameData gameData) {
		try {
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			Long bootserId = BaseGameContact.getLong(gameData.getBoosterId());
			Long level = BaseGameContact.getLong(gameData.getLevel()) + 1;

			if(StringUtils.isAnyEmpty(userId,gameId)){
				logger.error("使用道具参数为空:{},{}",userId,gameId);
				return BaseGameContact.failed("paramers is empty");
			}
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(userId,gameId));
				if(oldGameUserRef == null || oldGameUserRef.getHgameUserInfo() == null){
					logger.error("使用道具获取用户ref为空:{}",userId);
					return BaseGameContact.failed("get user game info failed");
				}

				String olBboostersCount = oldGameUserRef.getBoostersCount();
				if(StringUtils.isEmpty(olBboostersCount)){
					logger.error("使用道具获取道具数组为空:{}",userId);
					return BaseGameContact.failed("get game booters gold failed");
				}
				Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.spendGameUserRefboosters(userId,gameId,bootserId,olBboostersCount));
				if(BaseGameContact.isOkDb(dbIndex)){
					helpService.saveSpendBootserLog(userId,gameId,level,bootserId+1);
					return BaseGameContact.success(true);
				}
				logger.error("使用道具失败:{}",userId);
				return BaseGameContact.failed("Save game level up log failed");
			}
		} catch (Exception e) {
			logger.error("使用道具异常",e);
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
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			Long bootserId = BaseGameContact.getLong(gameData.getBoosterId());
			Long needGold = BaseGameContact.getLong(gameData.getNeedGold());
			Long level = BaseGameContact.getLong(gameData.getLevel()) + 1;
			if(StringUtils.isAnyEmpty(userId,gameId)){
				logger.error("购买道具参数为空:{},{}",userId,gameId);
				return BaseGameContact.failed("paramers is empty");
			}
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(userId,gameId));
				if(oldGameUserRef == null || oldGameUserRef.getHgameUserInfo() == null){
					logger.error("购买道具用户ref为空:{},{}",userId,gameId);
					return BaseGameContact.failed("get user game info failed");
				}
				Long oldGold = oldGameUserRef.getGold();
				HgameInfo hgameInfo = oldGameUserRef.getHgameInfo();
				String bootersGolds = hgameInfo.getBoostersGold();

				if(needGold < 1 || needGold > oldGold){
					logger.error("购买道具金币不足:{},{},{},{}",userId,gameId,oldGold,needGold);
					return BaseGameContact.failed("user gold not enough");
				}
				if(hgameInfo == null){
					logger.error("购买道具游戏信息为空:{},{}",userId,gameId);
					return BaseGameContact.failed("get game info failed");
				}
				if(StringUtils.isEmpty(bootersGolds)){
					logger.error("购买道具游戏金币数组为空:{},{}",userId,gameId);
					return BaseGameContact.failed("get game booters gold failed");
				}
				JSONArray array = JSONArray.parseArray(bootersGolds);
				Long value = array.getLongValue(bootserId.intValue());
				if(!needGold.equals(value)){
					String remarks = "道具违规:"+value+">"+needGold;
					helpService.saveAddBootserLog(userId,gameId,level,-needGold,bootserId+1,remarks);
					logger.error("购买道具违规:{},{}",userId,gameId);
					return BaseGameContact.failed("update game booters gold failed");
				}

				Long oldHbeans = oldGameUserRef.getHgameUserInfo().getHbeans();
				String olBboostersCount = oldGameUserRef.getBoostersCount();
				Integer userType = oldGameUserRef.getHgameUserInfo().getType();
				String token = oldGameUserRef.getHgameUserInfo().getToken();

				//app和本地乐豆同步
				String tag = "买道具";
				Boolean isSync = helpService.syncAppGold(level,userType,token,userId,gameId,-needGold,tag,oldHbeans);
				if(!isSync){
					logger.error("购买道具app和本地乐豆同步失败:{},{},{}",isSync,userId,gameId);
					return BaseGameContact.failed("update app user gold failed");
				}

				//更新用户游戏信息
				Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.addGameUserRefboosters(userId,gameId,bootserId,olBboostersCount,isSync));
				if(BaseGameContact.isOkDb(dbIndex)){
					String remarks = "购买道具:"+(bootserId+1);
					helpService.saveAddBootserLog(userId,gameId,level,-needGold,bootserId+1,remarks);
					return BaseGameContact.success(true);
				}
				logger.error("购买道具升级失败:{},{}",userId,gameId);
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
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			String playId = gameData.getPlayId();
			Long start = BaseGameContact.getLong(gameData.getStart());
			Long gold = BaseGameContact.getLong(gameData.getGold());
			Long score = BaseGameContact.getLong(gameData.getScore());
			Long level = BaseGameContact.getLong(gameData.getLevel()) + 1;
			if(StringUtils.isAnyEmpty(userId,gameId)){
				logger.error("游戏升级参数为空失败:{},{}",userId,gameId);
				return BaseGameContact.failed("paramers is empty");
			}
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(userId,gameId));
				if(oldGameUserRef == null || oldGameUserRef.getHgameUserInfo() == null){
					logger.error("游戏升级游戏信息为空失败:{},{}",userId,gameId);
					return BaseGameContact.failed("get user game info failed");
				}
				Integer type = DbGameContact.PLAY_TYPE_3;
				Long  oldGold = oldGameUserRef.getGold();
				Long  levelsCompleted = oldGameUserRef.getLevelsCompleted();
				String oldStarsPerLevel = oldGameUserRef.getStarsPerLevel();
				Integer userType = oldGameUserRef.getHgameUserInfo().getType();
				Long oldHbeans = oldGameUserRef.getHgameUserInfo().getHbeans();
				String  token = oldGameUserRef.getHgameUserInfo().getToken();

				//战绩重置
				List<HgamePlayRecord>  list = hgamePlayRecordDao.findList(DbGameContact.paramsGamePlayRecord(type,userId,gameId,level));
				if(list !=null && list.size() > 0){
					HgamePlayRecord oldRecord = list.get(0);
					Boolean isOk = this.updateBestGameRecord(oldRecord,userType,token,level,gold,score,start,oldStarsPerLevel,oldHbeans,oldGold,levelsCompleted);
					logger.info("修改战绩最好的游戏战局:{}",isOk);
					return BaseGameContact.success(isOk);
				}

				//初始化战局
				String remarks = "闯"+level+"关结算:"+gold;
				HgamePlayRecord newRecord = DbGameContact.initGamePlayRecord(userId,gameId,playId,type,level,gold,score,start,remarks);
				Long dbIndex = hgamePlayRecordDao.insert(newRecord);
				if(!BaseGameContact.isOkDb(dbIndex)){
					logger.error("游戏升级游戏保存记录失败:{},{}",userId,gameId);
					return BaseGameContact.failed("save init game record failed");
				}

				//app和本地乐豆同步
				String tag = "闯"+level+"关";
				Boolean isSync = helpService.syncAppGold(level,userType,token,userId,gameId,gold,tag,oldHbeans);
				if(!isSync){
					logger.error("游戏升级app和本地乐豆同步失败:{},{}",userId,gameId);
					return BaseGameContact.failed("update app user gold failed");
				}

				//更新用户游戏信息
				Boolean isLevelUp = false;
				if(start > 0){
					isLevelUp = true;
				}
				dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRef(userId,gameId,level,score,start,oldStarsPerLevel,isLevelUp));
				if(BaseGameContact.isOkDb(dbIndex) && gold != 0){
					helpService.saveGamelevelUpLog(userId,gameId,level,gold,score,oldGold);
					return BaseGameContact.success(true);
				}
				logger.error("游戏升级失败:{},{}",userId,gameId);
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
	public Boolean updateBestGameRecord(HgamePlayRecord oldRecord,Integer userType,String token,Long level, Long gold, Long score,Long start,String oldStarsPerLevel,Long oldHbeans,Long oldRefGold,Long levelsCompleted) {
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
				String tag = "重玩"+level+"关";
				Boolean isSync = helpService.syncAppGold(level,userType,token,userId,gameId,newGold,tag,oldHbeans);
				if(!isSync){
					logger.error("app和本地乐豆同步失败:{}",isSync);
					return false;
				}
				helpService.saveGameRestartLog(userId,gameId,level,newGold,newScore,oldRefGold);
			}
			if(!isUpdate){
				return false;
			}
			Long dbIndex = hgamePlayRecordDao.update(oldRecord);
			Boolean isOk = BaseGameContact.isOkDb(dbIndex);
			if(!isOk){
				logger.error("更新用户游戏记录失败:{},{}",userId,gameId);
				return false;
			}
			//更新用户游戏信息
			Boolean isLevelUp = false;
			if(levelsCompleted < level && start > 0){
				isLevelUp = true;
			}
			dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRef(userId,gameId,level,newScore,start,oldStarsPerLevel,isLevelUp));
			if(!BaseGameContact.isOkDb(dbIndex)){
				logger.error("更新用户游戏信息失败:{},{}",userId,gameId);
				return false;
			}
			return BaseGameContact.isOkDb(dbIndex);
		} catch (Exception e) {
			logger.error("修改战绩最好的游戏战局异常",e);
			return false;
		}
	}

	/***
	 * @desc获取用户信息
	 * @author nada
	 * @create 2021/1/12 5:51 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject getUserInfo(GameData gameData) {
		try {
			String uid = gameData.getUid();
			String gid = gameData.getGid();
			String ip = gameData.getIpAddress();
			if(StringUtils.isEmpty(uid)){
				return BaseGameContact.failed("token parameter is empty");
			}
			synchronized (uid) {
				HgameInfo hgameInfo =  helpService.getDefaultGameInfo(gid);
				if(hgameInfo == null){
					return BaseGameContact.failed("get gameinfo failed");
				}

				String gameId = hgameInfo.getId();
				HgameUserRef hgameUserRef = hgameUserRefDao.getByEntity(DbGameContact.paramsGameUserRef(uid,gameId));
				if(hgameUserRef != null){
					JSONObject response = DbGameContact.responseGameUserInfo(hgameInfo,hgameUserRef);
					logger.info("获取用户信息:{}",response);
					return BaseGameContact.success(response);
				}

				HgameUserInfo hgameUserInfo = hgameUserInfoDao.getByEntity(DbGameContact.paramsGameUserId(uid));
				if (hgameUserInfo == null) {
					//游客玩家
					Long seqId = hgameUserInfoDao.getNextSequence();
					HgameUserInfo initUserInfo = DbGameContact.initVisitorUserInfo(seqId, ip,uid);
					hgameUserInfoDao.addSequence();
					hgameUserRef = helpService.initGamUserRefe(initUserInfo, hgameInfo);
				}
				Long hBeans = hgameUserInfo.getHbeans();
				if(hgameUserRef == null){
					hgameUserRef = DbGameContact.initGameUserRef(hgameInfo,uid,BaseGameContact.getLong(hBeans));
					long db2 = hgameUserRefDao.insert(hgameUserRef);
					if(!BaseGameContact.isOkDb(db2)){
						return BaseGameContact.failed("init game user info failed");
					}
				}
				JSONObject response = DbGameContact.responseGameUserInfo(hgameInfo,hgameUserRef);
				logger.info("获取用户信息:{}",response);
				return BaseGameContact.success(response);
			}
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
			return BaseGameContact.failed("get userinfo error");
		}
	}

	/**
	 * @desc app地址初始化
	 * @author nada
	 * @create 2021/1/16 9:19 下午
	*/
	@Transactional(readOnly=false)
	public GameData init(String token,Integer type) {
		try {
			if(type == null || type < 1){
				type = 1;
			}
			synchronized (token) {
				List<HgameInfo> hgameInfoList = hgameInfoDao.findList(DbGameContact.paramsGameInfo(type));
				if(hgameInfoList == null || hgameInfoList.isEmpty()){
					return null;
				}

				HgameInfo hgameInfo = hgameInfoList.get(0);
				String gameId = hgameInfo.getId();
				GameData gameData = new GameData();
				gameData.setUrl(hgameInfo.getUrl());
				gameData.setGid(hgameInfo.getId());
				gameData.setVersion(hgameInfo.getVersion());
				gameData.setMinLimit(hgameInfo.getMinLimit());
				if(StringUtils.isEmpty(token)){
					return gameData;
				}
				JSONObject result = HttpGameContact.getUserInfo(token);
				Boolean isOk = BaseGameContact.isOk(result);
				if (!isOk) {
					return gameData;
				}
				//会员玩家
				JSONObject resData = result.getJSONObject("result");
				String parentId = resData.getString("userInfo_ID");
				Long hBeans = BaseGameContact.getLong(resData.getLong("userInfo_HBeans"));
				gameData.setGold(hBeans);
				//第一次访问会员信息初始化
				HgameUserInfo hgameUserInfo = hgameUserInfoDao.getByEntity(DbGameContact.paramsGameUserInfo(parentId));
				if (hgameUserInfo == null) {
					HgameUserInfo initUserInfo = DbGameContact.initMemberUserInfo(token, resData, DbGameContact.TYPE_MEMBER);
					helpService.initGamUserRefe(initUserInfo, hgameInfo);
					gameData.setUid(initUserInfo.getId());
					return gameData;
				}

				//老会员玩家，从app同步信息
				String userId = hgameUserInfo.getId();
				Long oldBeans = BaseGameContact.getLong(hgameUserInfo.getHbeans());
				gameData.setUid(userId);
				Long dbIndex = hgameUserInfoDao.update(DbGameContact.paramsGameUserInfoUpdate(userId,token,hBeans));
				if(!BaseGameContact.isOkDb(dbIndex)){
					return gameData;
				}
				if(hgameUserInfo.getType() != DbGameContact.TYPE_MEMBER){
					return gameData;
				}
				if(!oldBeans.equals(hBeans) && hBeans !=0 && hBeans > oldBeans){
					helpService.saveGameInitLog(userId,gameId,hBeans);
					hgameUserRefDao.updateResetGameUserRef(DbGameContact.updateGameUserRefGold(userId,gameId,hBeans));
					hgameUserInfoDao.updateRestartUserHbeans(DbGameContact.updateGameUserInfo(userId,hBeans));
				}
				return gameData;
			}
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
			return null;
		}
	}

}
