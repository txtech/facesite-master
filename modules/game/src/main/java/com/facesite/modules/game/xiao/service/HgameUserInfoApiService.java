/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.dao.HgamePlayRecordDao;
import com.facesite.modules.game.xiao.dao.HgameUserInfoDao;
import com.facesite.modules.game.xiao.dao.HgameUserRefDao;
import com.facesite.modules.game.xiao.entity.GameData;
import com.facesite.modules.game.xiao.entity.HgamePlayRecord;
import com.facesite.modules.game.xiao.entity.HgameUserInfo;
import com.facesite.modules.game.xiao.entity.HgameUserRef;
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

	/**
	 * @desc 游戏升级记录
	 * @author nada
	 * @create 2021/1/14 7:37 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject updateGamelevelUp(GameData gameData) {
		try {
			Long start = gameData.getStart();
			Long gold = gameData.getGold();
			Long score = gameData.getScore();
			Long level = gameData.getLevel() + 1;
			String userId = gameData.getUid();
			String gameId = gameData.getGid();
			String token = gameData.getToken();
			String playId = gameData.getPlayId();
			Integer type = DbGameContact.PLAY_TYPE_3;
			synchronized (userId){
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.getGameUserRefUser(userId,gameId));
				if(oldGameUserRef == null){
					return BaseGameContact.failed("get user game info failed");
				}
				Long oldTotalScore = oldGameUserRef.getTotalScore();
				//战绩重置
				List<HgamePlayRecord>  list = hgamePlayRecordDao.findList(DbGameContact.uniqueRecord(type,userId,gameId,level));
				if(list !=null && list.size() > 0){
					HgamePlayRecord oldRecord = list.get(0);
					Boolean isOk = this.updateBestGameRecord(oldRecord,oldTotalScore,token,level,gold,score,start);
					logger.info("修改战绩最好的游戏战局:{}",isOk);
					return BaseGameContact.success(isOk);
				}

				//app和本地乐豆同步
				String tag = "游戏升级结算:"+gold;
				Boolean isSync = this.synAppGold(token,userId,gameId,gold,tag);
				if(!isSync){
					logger.error("app和本地乐豆同步失败:{}",isSync);
					return BaseGameContact.failed("update app user gold failed");
				}

				//初始化战局
				HgamePlayRecord newRecord = DbGameContact.initRecord(userId,gameId,playId,type,level,gold,score,start,tag);
				Long dbIndex = hgamePlayRecordDao.insert(newRecord);
				if(!BaseGameContact.isOkDb(dbIndex)){
					return BaseGameContact.failed("save init game record failed");
				}

				//更新用户游戏信息
				dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRef(userId,gameId,level,score,oldTotalScore));
				if(BaseGameContact.isOkDb(dbIndex)){
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
	public Boolean updateBestGameRecord(HgamePlayRecord oldRecord,Long oldTotalScore,String token,Long level, Long gold, Long score,Long start) {
		try {
			Long oldGold = oldRecord.getGold();
			Long oldScore = oldRecord.getScore();
			Long oldLevel = oldRecord.getLevel();
			Long oldStart = oldRecord.getStart();
			if(!(level).equals(oldLevel)){
				return false;
			}
			Long newRecord = 0L;
			Boolean isUpdate = false;
			if(gold > oldGold){
				isUpdate = true;
				oldRecord.setGold(gold);
				Long newGold = gold - oldGold;
				String tag = "游戏重玩结算:"+gold;
				Boolean isSync = this.synAppGold(token,oldRecord.getUserId(),oldRecord.getGameId(),newGold,tag);
				if(!isSync){
					logger.error("app和本地乐豆同步失败:{}",isSync);
					return false;
				}
			}
			if(score > oldScore){
				isUpdate = true;
				oldRecord.setScore(score);
				newRecord = score - oldScore;
				//更新用户游戏信息
				Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.resetGameUserRef(oldRecord.getUserId(),oldRecord.getGameId(),oldTotalScore,newRecord));
				if(BaseGameContact.isOkDb(dbIndex)){
					logger.error("更新用户游戏信息失败:{}",dbIndex);
					return false;
				}
			}
			if(start > oldStart){
				isUpdate = true;
				oldRecord.setStart(start);
			}
			if(!isUpdate){
				return false;
			}
			Long dbIndex = hgamePlayRecordDao.update(oldRecord);
			Boolean isOk = BaseGameContact.isOkDb(dbIndex);
			if(!isOk){
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
	public Boolean synAppGold(String token,String userId,String gameId, Long gold,String tag) {
		JSONObject result = HttpGameContact.updateAppGold(token,gold,tag);
		Boolean isOk = BaseGameContact.isOk(result);
		if(!isOk){
			return false;
		}
		Long dbIndex = hgameUserRefDao.updateGameUserGold(DbGameContact.updateGameUserGold(userId,gameId,gold));
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
			String token = gameData.getToken();
			if(StringUtils.isEmpty(token)){
				return BaseGameContact.failed("token parameter is empty");
			}
			synchronized (token){
				JSONObject result = HttpGameContact.getUserInfo(token);
				Boolean isOk = BaseGameContact.isOk(result);
				HgameUserInfo initUserInfo = null;
				if(!isOk){
					//初始化游客玩家
					initUserInfo = DbGameContact.initVisitorUserInfo(token);
				}else{
					//会员玩家同步信息
					JSONObject resData = result.getJSONObject("result");
					HgameUserInfo params = DbGameContact.getUserInfoParent(resData.getString("userInfo_ID"));
					HgameUserInfo hgameUserInfo = hgameUserInfoDao.getByEntity(params);
					if(hgameUserInfo !=null){
						String userId = hgameUserInfo.getId();
						HgameUserRef hgameUserRef = this.getGameDb(userId);
						return BaseGameContact.success(DbGameContact.getGameUserInfo(hgameUserInfo,hgameUserRef));
					}
					//初始化会员玩家
					initUserInfo = DbGameContact.saveUserInfo(token,resData,DbGameContact.TYPE_MEMBER);
				}
				HgameUserRef hgameUserRef = this.initGameDb(initUserInfo);
				if(hgameUserRef == null){
					return BaseGameContact.failed("init userinfo failed");
				}
				return BaseGameContact.success(DbGameContact.getGameUserInfo(initUserInfo,hgameUserRef));
			}
		} catch (Exception e) {
			Console.log("获取用户信息异常",e);
			return BaseGameContact.failed("get userinfo error");
		}
	}

	/**
	 * @desc 获取用户游戏信息
	 * @author nada
	 * @create 2021/1/12 7:31 下午
	*/
	@Transactional(readOnly=false)
	public HgameUserRef getGameDb(String userId){
		try {
			HgameUserRef userRefparams = DbGameContact.getGameUserRefUserId(userId);
			HgameUserRef hgameUserRef = hgameUserRefDao.getByEntity(userRefparams);
			if(hgameUserRef != null){
				return hgameUserRef;
			}
			hgameUserRef = DbGameContact.initGameUserRef("1",userId);
			long db2 = hgameUserRefDao.insert(hgameUserRef);
			if(!BaseGameContact.isOkDb(db2)){
				return null;
			}
			return hgameUserRef;
		} catch (Exception e) {
			Console.log("获取用户游戏信息异常",e);
			return null;
		}
	}

	/**
	 * @desc 会员玩家初始化游戏
	 * @author nada
	 * @create 2021/1/12 7:25 下午
	*/
	@Transactional(readOnly=false)
	public HgameUserRef initGameDb(HgameUserInfo initUserInfo){
		try {
			long db1 = hgameUserInfoDao.insert(initUserInfo);
			if(!BaseGameContact.isOkDb(db1)){
				return null;
			}
			String userId = initUserInfo.getId();
			HgameUserRef hgameUserRef = DbGameContact.initGameUserRef("1",userId);
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
