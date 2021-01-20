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
	public Boolean syncAppGold(Long level,Integer userType,String token,String userId,String gameId, Long hBeans,String tag,Long oldHbeans) {
		if(DbGameContact.TYPE_MEMBER == userType  && hBeans !=0 ){
			JSONObject result = HttpGameContact.updateAppGold(token,hBeans,tag);
			Boolean isOk = BaseGameContact.isOk(result);
			String remarks = "";
			if(!isOk){
				logger.error("app和本地乐豆同步失败:{}",result);
				remarks = tag+"失败:"+ (oldHbeans);
			}else{
				remarks = tag+"呵豆:"+ (oldHbeans + hBeans);
			}
			this.saveSyncGoldHbeanLog(userId,gameId,level,hBeans,remarks);
		}
		Long dbIndex = hgameUserRefDao.updateGameUserRef(DbGameContact.updateGameUserRefGold(userId,gameId,hBeans));
		if(!BaseGameContact.isOkDb(dbIndex)){
			return false;
		}
		dbIndex = hgameUserInfoDao.updateUserHbeans(DbGameContact.updateGameUserInfo(userId,hBeans));
		if(BaseGameContact.isOkDb(dbIndex)){
			return true;
		}
		return false;
	}

	/**
	 * @desc 呵豆金币同步日志
	 * @author nada
	 * @create 2021/1/19 9:26 下午
	 */
	@Transactional(readOnly=false)
	public Boolean saveSyncGoldHbeanLog(String userId,String gameId,Long level,Long gole,String remarks){
		try {
			Long score = 0L;
			Long bootserId = 0L;
			return this.saveLog(DbGameContact.LOG_TYPE_1,userId,gameId,level,gole,score,bootserId,remarks);
		} catch (Exception e) {
			logger.error("保存购买道具日志异常",e);
			return false;
		}
	}
	/**
	 * @desc 游戏重玩日志
	 * @author nada
	 * @create 2021/1/19 9:26 下午
	 */
	@Transactional(readOnly=false)
	public Boolean saveGameInitLog(String userId,String gameId,Long gole){
		try {
			Long level = 0L;
			Long score = 0L;
			Long bootserId = 0L;
			String remarks = "进游戏重置:"+gole;
			return this.saveLog(DbGameContact.LOG_TYPE_2,userId,gameId,level,gole,score,bootserId,remarks);
		} catch (Exception e) {
			logger.error("保存购买道具日志异常",e);
			return false;
		}
	}
	/**
	 * @desc 游戏升级日志
	 * @author nada
	 * @create 2021/1/19 9:26 下午
	*/
	@Transactional(readOnly=false)
	public Boolean saveGamelevelUpLog(String userId,String gameId,Long level,Long gole,Long score,Long oldGold){
		try {
			Long bootserId = 0L;
			String remarks = "闯关升级:"+ (oldGold+gole);
			return this.saveLog(DbGameContact.LOG_TYPE_2,userId,gameId,level,gole,score,bootserId,remarks);
		} catch (Exception e) {
			logger.error("保存购买道具日志异常",e);
			return false;
		}
	}
	/**
	 * @desc 游戏重玩日志
	 * @author nada
	 * @create 2021/1/19 9:26 下午
	 */
	@Transactional(readOnly=false)
	public Boolean saveGameRestartLog(String userId,String gameId,Long level,Long gole,Long score,Long oldGold){
		try {
			Long bootserId = 0L;
			String remarks = "重玩闯关:"+(oldGold+ gole);
			return this.saveLog(DbGameContact.LOG_TYPE_2,userId,gameId,level,gole,score,bootserId,remarks);
		} catch (Exception e) {
			logger.error("保存购买道具日志异常",e);
			return false;
		}
	}

	/**
	 * @desc 购买道具日志
	 * @author nada
	 * @create 2021/1/19 9:21 下午
	*/
	@Transactional(readOnly=false)
	public Boolean saveAddBootserLog(String userId,String gameId,Long level,Long gole,Long bootserId,String remarks){
		try {
			Long score = 0L;
			return this.saveLog(DbGameContact.LOG_TYPE_3,userId,gameId,level,gole,score,bootserId+1,remarks);
		} catch (Exception e) {
			logger.error("保存购买道具日志异常",e);
			return false;
		}
	}
	/**
	 * @desc 使用道具日志
	 * 1:同步呵豆日志 2:金币日志 3:道具日志
	 * @author nada
	 * @create 2021/1/19 9:05 下午
	*/
	@Transactional(readOnly=false)
	public Boolean saveSpendBootserLog(String userId,String gameId,Long level,Long bootserId){
		try {
			Long gole = 0L;
			Long score = 0L;
			String remarks = "使用道具:"+(bootserId+1);
			return this.saveLog(DbGameContact.LOG_TYPE_3,userId,gameId,level,gole,score,bootserId+1,remarks);
		} catch (Exception e) {
			logger.error("保存使用道具日志日志异常",e);
			return false;
		}
	}

	/**
	 * @desc 保存日志
	 * 1:同步呵豆日志 2:金币日志 3:道具日志
	 * @author nada
	 * @create 2021/1/19 9:05 下午
	 */
	@Transactional(readOnly=false)
	public Boolean saveLog(Integer type,String userId,String gameId,Long level,Long gole,Long score,Long bootserId,String remarks){
		try {
			Long dbIndex = hgamePlayLogDao.insert(DbGameContact.saveLog(type,userId,gameId,level,gole,score,bootserId,remarks));
			if(BaseGameContact.isOkDb(dbIndex)){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("保存日志异常",e);
			return false;
		}
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
