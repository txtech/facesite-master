/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service.simple;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.common.InstanceUtils;
import com.nabobsite.modules.nabob.api.common.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.sys.dao.SysConfigDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.task.dao.TaskInfoDao;
import com.nabobsite.modules.nabob.cms.task.dao.TaskUserRewardDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;
import com.nabobsite.modules.nabob.cms.team.dao.TeamUserDao;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.user.dao.*;
import com.nabobsite.modules.nabob.cms.user.entity.*;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
public class SimpleUserService extends CrudService<UserInfoDao, UserInfo> {
	@Autowired
	public RedisOpsUtil redisOpsUtil;
	@Autowired
	public UserInfoDao userInfoDao;
	@Autowired
	public TeamUserDao teamUserDao;
	@Autowired
	public SysConfigDao sysConfigDao;
	@Autowired
	public UserAccountDao userAccountDao;
	@Autowired
	public TaskInfoDao taskInfoDao;
	@Autowired
	public UserAccountTaskDao userTaskDao;
	@Autowired
	public UserInfoMembershipDao memberShipDao;
	@Autowired
	public TaskUserRewardDao userTaskRewardDao;
	@Autowired
	public UserAccountWarehouseDao userAccountWarehouseDao;

	/**
	 * @desc 保存用户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean saveUserAndAccount(UserInfo initUser) {
		try {
			long dbResult = userInfoDao.insert(initUser);
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			//修改邀请秘文
			String userId = initUser.getId();
			String parent1UserId = initUser.getParentSysId();
			this.updateUserSecret(userId,parent1UserId);
			//初始化总账户
			dbResult = userAccountDao.insert(InstanceUtils.initUserAccount(userId));
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			//初始化云仓库账户
			dbResult = userAccountWarehouseDao.insert(InstanceUtils.initUserAccountWarehouse(userId));
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			//初始化奖励账户
			dbResult = userTaskDao.insert(InstanceUtils.initUserTask(userId));
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			//初始化团队信息
			dbResult = teamUserDao.insert(InstanceUtils.initUserTeam(userId));
			if(!ContactUtils.dbResult(dbResult)){
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("保存用户异常,{}",e);
			return true;
		}
	}

	/**
	 * @desc 修改用户邀请秘文
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateUserSecret(String userId,String parentSysId) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return false;
			}
			UserInfo userInfo = new UserInfo();
			JSONObject secretJson = new JSONObject();
			secretJson.put("pid",userId);
			secretJson.put("sid",parentSysId);
			userInfo.setId(userId);
			userInfo.setInviteSecret( HiDesUtils.desEnCode(secretJson.toString()));
			long dbResult = userInfoDao.update(userInfo);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改用户邀请秘文异常,{}",userId,e);
			return true;
		}
	}

	/**
	 * @desc 用户解锁
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateLock(String userId,int userLock) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			long dbResult = userInfoDao.update(userInfo);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("用户解锁异常",e);
			return null;
		}
	}

	/**
	 * @desc 修改团队数量
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateTeamNum(String userId,int num) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			TeamUser teamUser = new TeamUser();
			teamUser.setId(userId);
			teamUser.setTeam1Num(num);
			long dbResult = teamUserDao.updateTeamNum(teamUser);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改团队数量异常",e);
			return null;
		}
	}

	/**
	 * @desc 修改直推团队数量
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateDirectTeamNum(String userId,String parent1UserId,int num) {
		try {
			if(!ContactUtils.isOkUserId(parent1UserId)){
				return null;
			}
			TeamUser teamUser = new TeamUser();
			teamUser.setId(userId);
			teamUser.setValidNum(num);
			long dbResult = teamUserDao.updateTeamNum(teamUser);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改团队数量异常",e);
			return null;
		}
	}

	/**
	 * @desc 账号等级升级和解锁
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean updateUpLevelAdnLock(String userId,int upLevel,int userLock,int valid) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			userInfo.setLevel(upLevel);
			userInfo.setIsValid(valid);
			long dbResult = userInfoDao.update(userInfo);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("账号等级升级和解锁异常",e);
			return null;
		}
	}

	/**
	 * @desc 修改用户登陆IP
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateLoginIp(String userId,String ip) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return false;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLoginIp(ip);
			long dbResult = userInfoDao.update(userInfo);
			return ContactUtils.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改用户登陆IP异常,{}",userId,e);
			return true;
		}
	}


	/**
	 * @desc 获取用户账户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserAccount getUserAccountByUserId(String userId) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserAccount userAccount = new UserAccount();
			userAccount.setUserId(userId);
			return userAccountDao.getByEntity(userAccount);
		} catch (Exception e) {
			logger.error("获取用户账户信息异常,{}",userId,e);
			return null;
		}
	}
	/**
	 * @desc 获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByAccountNo(String accountNo) {
		try {
			if(StringUtils.isEmpty(accountNo)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setAccountNo(accountNo);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			logger.error("获取用户信息异常,{}",accountNo,e);
			return null;
		}
	}
	/**
	 * @desc 获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByInviteCode(String inviteCode) {
		try {
			if(StringUtils.isEmpty(inviteCode)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setInviteCode(inviteCode);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			logger.error("获取用户信息异常,{}",inviteCode,e);
			return null;
		}
	}
	/**
	 * @desc 获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			logger.error("获取用户信息异常,{}",userId,e);
			return null;
		}
	}
	/**
	 * @desc 获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByToken(String token) {
		try {
			if(StringUtils.isEmpty(token)){
				return null;
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = this.getUserInfoByUserId(userId);
			if(userInfo == null){
				return null;
			}
			return userInfo;
		} catch (Exception e) {
			logger.error("获取用户信息异常,{}",token,e);
			return null;
		}
	}
	/**
	 * @desc 获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public String getUserIdByToken(String token) {
		try {
			if(StringUtils.isEmpty(token)){
				return null;
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = this.getUserInfoByUserId(userId);
			if(userInfo == null){
				return null;
			}
			return userInfo.getId();
		} catch (Exception e) {
			logger.error("获取用户信息异常,{}",token,e);
			return "";
		}
	}




	/**
	 * @desc 获取配置列表
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public List<SysConfig> getSysConfigList() {
		try {
			SysConfig sysConfig = new SysConfig();
			sysConfig.setStatus("1");
			List<SysConfig> list = sysConfigDao.findList(sysConfig);
			return list;
		} catch (Exception e) {
			logger.error("获取配置列表异常",e);
			return null;
		}
	}
	/**
	 * @desc 获取配置
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public SysConfig getSysConfigByKey(String key) {
		try {
			if(StringUtils.isEmpty(key)){
				return null;
			}
			SysConfig sysConfig = new SysConfig();
			sysConfig.setKey(key);
			return sysConfigDao.getByEntity(sysConfig);
		} catch (Exception e) {
			logger.error("获取配置异常,{}",key,e);
			return null;
		}
	}


	/**
	 * @desc 获取任务详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	public TaskInfo getTaskInfoById(String id) {
		try {
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setId(id);
			return taskInfoDao.getByEntity(taskInfo);
		} catch (Exception e) {
			logger.error("获取任务详情异常",e);
			return null;
		}
	}
	/**
	 * @desc 获取用户任务
	 * @author nada
	 * @create 2021/5/13 7:32 下午
	 */
	public UserAccountTask getUserTaskByUserId(String userId){
		try {
			UserAccountTask userTaskPrams = new UserAccountTask();
			userTaskPrams.setUserId(userId);
			UserAccountTask userTask = userTaskDao.getByEntity(userTaskPrams);
			return userTask;
		} catch (Exception e) {
			logger.error("获取用户任务异常",e);
			return null;
		}
	}
	/**
	 * @desc 获取用户任务完成情况
	 * @author nada
	 * @create 2021/5/13 7:32 下午
	 */
	public JSONObject getUserTaskNumJsonByUserId(String userId) {
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			UserAccountTask userTaskPrams = new UserAccountTask();
			userTaskPrams.setUserId(userId);
			UserAccountTask userTask = userTaskDao.getByEntity(userTaskPrams);
			if (userTask != null) {
				String finishData = userTask.getTaskFinishData();
				return ContactUtils.str2JSONObject(finishData);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取团队用户信息
	 * @param userId
	 * @return
	 */
	public TeamUser getTeamUserByUserId(String userId){
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return null;
			}
			TeamUser teamUser = new TeamUser();
			teamUser.setId(userId);
			return teamUserDao.get(teamUser);
		} catch (Exception e) {
			logger.error("获取用户团队个数异常",e);
			return null;
		}
	}
	/**
	 * @desc 团队直推有效个数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public int getTeamUserValidNum(String userId){
		try {
			if(!ContactUtils.isOkUserId(userId)){
				return 0;
			}
			TeamUser teamUser = this.getTeamUserByUserId(userId);
			if(teamUser == null){
				return 0;
			}
			int teamNum = teamUser.getValidNum();
			return teamNum;
		} catch (Exception e) {
			logger.error("团队直推有效个数异常",e);
			return 0;
		}
	}

	/**
	 * 获取会员权益列表
	 */
	public List<UserInfoMembership> getMemberShipList(){
		try {
			return memberShipDao.findList(new UserInfoMembership());
		} catch (Exception e) {
			logger.error("获取会员权益列表异常",e);
			return null;
		}
	}
	/**
	 * 获取会员权益列表
	 */
	public UserInfoMembership getMemberShipByLevel(int level){
		try {
			UserInfoMembership parms = new UserInfoMembership();
			parms.setLevel(level);
			return memberShipDao.getByEntity(parms);
		} catch (Exception e) {
			logger.error("获取会员权益列表异常",e);
			return null;
		}
	}
	/**
	 * @desc 获取用户解锁状态：1：解锁 2：锁定
	 * @author nada
	 * @create 2021/5/12 11:22 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public int getUserLock(String userId,int currentLevel,BigDecimal payMoney){
		if(currentLevel == ContactUtils.USER_LEVEL_0 || currentLevel == ContactUtils.USER_LEVEL_1){
			return ContactUtils.USER_LOCK_1;
		}
		UserInfoMembership memberShip = this.getMemberShipByLevel(currentLevel);
		if(memberShip == null){
			return ContactUtils.USER_LOCK_2;
		}
		BigDecimal mustBalance = memberShip.getGradeMoney();
		if(ContactUtils.isBiggerOrEqual(payMoney,mustBalance)){
			return ContactUtils.USER_LOCK_1;
		}
		int teamNum = this.getTeamUserValidNum(userId);
		if(teamNum >= ContactUtils.USER_LEVEL_UP_TEAM_NUM){
			return ContactUtils.USER_LOCK_1;
		}
		return ContactUtils.USER_LOCK_2;
	}
	/**
	 * 获取会员权益最大等级
	 */
	public int getMemberShipMaxLevel(BigDecimal amount){
		try {
			int maxLevel = 0;
			BigDecimal maxAmount = amount;
			if(ContactUtils.isLesserOrEqualZero(maxAmount)){
				return maxLevel;
			}
			UserInfoMembership parms = new UserInfoMembership();
			parms.getSqlMap().getWhere().and("grade_money", QueryType.GTE, maxAmount);
			List<UserInfoMembership> memberShipList = memberShipDao.findList(parms);
			for (UserInfoMembership  memberShip : memberShipList) {
				BigDecimal gradeMoney = memberShip.getGradeMoney();
				if(ContactUtils.isBiggerOrEqual(gradeMoney,maxAmount)){
					maxLevel = memberShip.getLevel();
					maxAmount = memberShip.getGradeMoney();
				}
			}
			return maxLevel;
		} catch (Exception e) {
			logger.error("获取会员权益最大等级异常",e);
			return 0;
		}
	}


	/**
	 * @desc 加密铭感信息
	 * @author nada
	 * @create 2021/5/12 11:07 上午
	*/
	public String decodeAccountAndPwd(String accountNoEntry, String passwordEntry) {
		if(StringUtils.isAnyBlank(accountNoEntry,passwordEntry)){
			return "账号或密码不能为空";
		}
		String secretKey = Global.getConfig("shiro.loginSubmit.secretKey");
		String accountNo = DesUtils.decode(accountNoEntry, secretKey);
		String password = DesUtils.decode(passwordEntry, secretKey);
		String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
		return md5Pass;
	}
}
