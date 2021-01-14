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
	 * @desc 游戏开始记录
	 * @author nada
	 * @create 2021/1/14 7:37 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject updateGameStart(GameData gameData) {
		try {
			HgamePlayRecord hgamePlayRecord = DbGameContact.initRecord(gameData,DbGameContact.PLAY_TYPE_2);
			HgamePlayRecord parms = DbGameContact.getUniqueRecord(hgamePlayRecord);
			List<HgamePlayRecord>  list = hgamePlayRecordDao.findList(parms);
			if(list !=null && list.size() > 0){
				return BaseGameContact.success(true);
			}
			Long dbIndex = hgamePlayRecordDao.insert(hgamePlayRecord);
			if(dbIndex > 0){
				return BaseGameContact.success(true);
			}
			return BaseGameContact.failed("Save game start log failed");
		} catch (Exception e) {
			logger.error("游戏开始记录异常",e);
			return BaseGameContact.failed("Save game start log error");
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
			synchronized (gameData.getUid()) {
				HgamePlayRecord hgamePlayRecord = DbGameContact.initRecord(gameData,DbGameContact.PLAY_TYPE_3);
				HgamePlayRecord parms = DbGameContact.getUniqueRecord(hgamePlayRecord);
				List<HgamePlayRecord>  list = hgamePlayRecordDao.findList(parms);
				if(list !=null && list.size() > 0){
					return BaseGameContact.success(true);
				}
				Long dbIndex = hgamePlayRecordDao.insert(hgamePlayRecord);
				if(!BaseGameContact.isOkDb(dbIndex)){
					return BaseGameContact.failed("save game record failed");
				}
				HgameUserRef oldGameUserRef = hgameUserRefDao.getByEntity(DbGameContact.getGameUserRefUserId(gameData.getUid()));
				if(oldGameUserRef == null){
					return BaseGameContact.failed("get user game info failed");
				}
				HgameUserRef updateGameUserRef = DbGameContact.updateGameUserRef(oldGameUserRef,hgamePlayRecord);
				String token = gameData.getToken();
				JSONObject postUpdateResult = HttpGameContact.postUpdateAccount(token,hgamePlayRecord.getGold(),"游戏升级结算:"+hgamePlayRecord.getGold());
				Boolean isOk = BaseGameContact.isOk(postUpdateResult);
				if(isOk){
					dbIndex = hgameUserRefDao.updateGameUserGold(updateGameUserRef);
					if(!BaseGameContact.isOkDb(dbIndex)){
						return BaseGameContact.failed("update game gold failed");
					}
				}
				dbIndex = hgameUserRefDao.updateGameUserRef(updateGameUserRef);
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
