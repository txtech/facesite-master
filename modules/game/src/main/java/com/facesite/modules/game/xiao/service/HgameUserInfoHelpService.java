/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

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
public class HgameUserInfoHelpService extends CrudService<HgameUserInfoDao, HgameUserInfo> {

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
	 * @desc app和本地乐豆同步
	 * @author nada
	 * @create 2021/1/15 10:05 上午
	 */
	@Transactional(readOnly=false)
	public Boolean syncAppGold(Long level,Integer userType,String token,String userId,String gameId, Long gold,Long socre,String tag,Long oldHbeans) {
		if(DbGameContact.TYPE_MEMBER == userType  && gold !=0 ){
			JSONObject result = HttpGameContact.updateAppGold(token,gold,tag);
			Boolean isOk = BaseGameContact.isOk(result);
			String remarks = "";
			if(!isOk){
				logger.error("app和本地乐豆同步失败:{}",result);
				remarks = "同步呵豆失败:"+ (oldHbeans);
			}else{
				remarks = "同步呵豆成功:"+ (oldHbeans + gold);
			}
			hgamePlayLogDao.insert(DbGameContact.saveLog(DbGameContact.LOG_TYPE_1,userId,gameId,level,gold,socre,0L,remarks));
		}
		Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRefGold(userId,gameId,gold));
		if(BaseGameContact.isOkDb(dbIndex)){
			return true;
		}
		return false;
	}

	/*
	 * @desc 获取游戏配置
	 * @author nada
	 * @create 2021/1/18 9:38 下午
	 */
	public HgameInfo getDefaultGameInfo(String gid){
		List<HgameInfo> gameInfoList = null;
		if(StringUtils.isNotEmpty(gid)){
			gameInfoList = hgameInfoDao.findList(DbGameContact.paramsGameInfo(gid));
		}
		if(gameInfoList == null || gameInfoList.isEmpty()){
			gameInfoList = hgameInfoDao.findList(DbGameContact.paramsGameInfo(1));
		}
		if(gameInfoList == null || gameInfoList.isEmpty()){
			return null;
		}
		return  gameInfoList.get(0);
	}

	/**
	 * @desc 会员玩家初始化游戏
	 * @author nada
	 * @create 2021/1/12 7:25 下午
	 */
	@Transactional(readOnly=false)
	public HgameUserRef initGamUserRefe(HgameUserInfo initUserInfo,HgameInfo hgameInfo){
		try {
			long db1 = hgameUserInfoDao.insert(initUserInfo);
			if(!BaseGameContact.isOkDb(db1)){
				return null;
			}
			String userId = initUserInfo.getId();
			Long gold = BaseGameContact.getLong(initUserInfo.getHbeans());
			HgameUserRef hgameUserRef = DbGameContact.initGameUserRef(hgameInfo,userId,gold);
			long db2 = hgameUserRefDao.insert(hgameUserRef);
			if(!BaseGameContact.isOkDb(db2)){
				return null;
			}
			return hgameUserRef;
		} catch (Exception e) {
			logger.error("初始化游戏异常",e);
			return null;
		}
	}
}
