/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONObject;
import com.facesite.modules.game.xiao.dao.HgameUserInfoDao;
import com.facesite.modules.game.xiao.dao.HgameUserRefDao;
import com.facesite.modules.game.xiao.entity.HgameUserInfo;
import com.facesite.modules.game.xiao.entity.HgameUserRef;
import com.facesite.modules.game.xiao.utils.BaseGameContact;
import com.facesite.modules.game.xiao.utils.DbGameContact;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/***
	 * @desc获取用户信息
	 * @author nada
	 * @create 2021/1/12 5:51 下午
	*/
	@Transactional(readOnly=false)
	public JSONObject getUserInfo(String token) {
		try {
			if(StringUtils.isEmpty(token)){
				return BaseGameContact.failed("token parameter is empty");
			}
			synchronized (token){
				JSONObject result = BaseGameContact.getUserInfo(token);
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
						Long userId = Long.valueOf(hgameUserInfo.getId());
						HgameUserRef hgameUserRef = this.getGameDb(userId);
						return DbGameContact.getGameUserInfo(hgameUserInfo,hgameUserRef);
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
	public HgameUserRef getGameDb(Long userId){
		try {
			HgameUserRef userRefparams = DbGameContact.getGameUserRefUserId(userId);
			HgameUserRef hgameUserRef = hgameUserRefDao.getByEntity(userRefparams);
			if(hgameUserRef != null){
				return hgameUserRef;
			}
			hgameUserRef = DbGameContact.initGameUserRef(1L,userId);
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
			Long userId = Long.valueOf(initUserInfo.getId());
			HgameUserRef hgameUserRef = DbGameContact.initGameUserRef(1L,userId);
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
