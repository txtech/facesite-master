/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.sys.dao.SysConfigDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class BaseUserService extends CrudService<UserInfoDao, UserInfo> {
	@Autowired
	protected RedisOpsUtil redisOpsUtil;
	@Autowired
	protected UserInfoDao userInfoDao;
	@Autowired
	protected SysConfigDao sysConfigDao;
	@Autowired
	protected UserAccountDao userAccountDao;

	/**
	 * @desc 初始化用户账户
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean saveInitUserAccount(String userId) {
		try {
			long dbResult = userAccountDao.insert(InstanceContact.initUserAccount(userId));
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("初始化用户账户异常,{}",userId,e);
			return true;
		}
	}

	/**
	 * @desc 修改用户语言
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateUserLang(String userId,String lang) {
		try {
			if(!CommonContact.isOkUserId(userId)){
				return false;
			}
			lang = I18nUtils.getLangStandard(lang);
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLang(lang);
			long dbResult = userInfoDao.update(userInfo);
			I18nUtils.setUserLang(userId,lang);
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改用户语言异常,{}",userId,e);
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
			if(!CommonContact.isOkUserId(userId)){
				return false;
			}
			UserInfo userInfo = new UserInfo();
			JSONObject secretJson = new JSONObject();
			secretJson.put("pid",userId);
			secretJson.put("sid",parentSysId);
			userInfo.setId(userId);
			userInfo.setInviteSecret( HiDesUtils.desEnCode(secretJson.toString()));
			long dbResult = userInfoDao.update(userInfo);
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改用户邀请秘文异常,{}",userId,e);
			return true;
		}
	}

	/**
	 * @desc 修改登陆IP
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public boolean updateLoginIp(String userId,String ip) {
		try {
			if(!CommonContact.isOkUserId(userId)){
				return false;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLoginIp(ip);
			long dbResult = userInfoDao.update(userInfo);
			return CommonContact.dbResult(dbResult);
		} catch (Exception e) {
			logger.error("修改登陆IP异常,{}",userId,e);
			return true;
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
			if(!CommonContact.isOkUserId(userId)){
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
			String newTokenKey = RedisPrefixContant.getTokenUserKey(token);
			String userTokenKey = RedisPrefixContant.getUserTokenKey(userId);
			redisOpsUtil.set(newTokenKey,userId,RedisPrefixContant.CACHE_HALF_HOUR);
			redisOpsUtil.set(userTokenKey,token,RedisPrefixContant.CACHE_HALF_HOUR);
			return userInfo;
		} catch (Exception e) {
			logger.error("获取用户信息异常,{}",token,e);
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
	 * @desc 获取配置列表
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public List<SysConfig> getSysConfigList() {
		try {
			SysConfig sysConfig = new SysConfig();
			List<SysConfig> list = sysConfigDao.findList(sysConfig);
			return list;
		} catch (Exception e) {
			logger.error("获取配置列表异常",e);
			return null;
		}
	}

	/**
	 * @desc 获取账户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional (readOnly = true, rollbackFor = Exception.class)
	public UserAccount getUserAccountByUserId(String userId) {
		try {
			if(!CommonContact.isOkUserId(userId)){
				return null;
			}
			UserAccount userAccount = new UserAccount();
			userAccount.setUserId(userId);
			return userAccountDao.getByEntity(userAccount);
		} catch (Exception e) {
			logger.error("获取账户信息异常,{}",userId,e);
			return null;
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
