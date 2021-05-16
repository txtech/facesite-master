/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.cms.base.service.SequenceService;
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.interceptor.I18nInterceptor;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserInfoApiService extends BaseUserService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private TriggerApiService triggerApiService;

	/**
	 * @desc 用户修改密码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> updatePwd(String token,UserInfoModel userInfoModel) {
		try {
			String accountNo = userInfoModel.getAccountNo();
			String oldPassword = userInfoModel.getOldPassword();
			String newPassword = userInfoModel.getPassword();
			if(StringUtils.isAnyEmpty(accountNo,oldPassword,newPassword)){
				return ResultUtil.failed("修改失败,修改信息为空");
			}
			UserInfo oldUserInfo = this.getUserInfoByToken(token);
			if(oldUserInfo == null){
				return ResultUtil.failed("修改失败,获取帐号信息为空");
			}
			if (oldPassword.equalsIgnoreCase(newPassword)) {
				return ResultUtil.failed("修改失败,新旧密码不能一样");
			}
			String oldDbPwd = oldUserInfo.getPassword();
			String md5OldPwd = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
			if (!oldDbPwd.equalsIgnoreCase(md5OldPwd)) {
				return ResultUtil.failed("修改失败,旧密码输入错误");
			}
			if(!accountNo.equalsIgnoreCase(oldUserInfo.getAccountNo())){
				return ResultUtil.failed("修改失败,账户输入错误");
			}
			String md5NewPwd = DigestUtils.md5DigestAsHex(newPassword.getBytes());
			UserInfo updateUserInfo = new UserInfo();
			updateUserInfo.setId(oldUserInfo.getId());
			updateUserInfo.setPassword(md5NewPwd);
			long dbResult = userInfoDao.update(updateUserInfo);
			if(CommonContact.dbResult(dbResult)){
				this.logout(token);
				return ResultUtil.success(true);
			}
			return ResultUtil.failed("Failed to update password!");
		} catch (Exception e) {
			logger.error("用户修改密码异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 用户退出
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> logout(String token) {
		try {
			redisOpsUtil.remove(RedisPrefixContant.getTokenUserKey(token));
			return ResultUtil.success(Boolean.TRUE);
		} catch (Exception e) {
			logger.error("用户退出异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 获取邀请好友链接
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<String> shareFriends(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed("获取失败,获取帐号信息为空");
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("pid",userInfo.getId());
			jsonObject.put("sid",userInfo.getParentSysId());
			String registerUrl = "param_parent="+ HiDesUtils.desEnCode(jsonObject.toString());
			return ResultUtil.success(registerUrl);
		} catch (Exception e) {
			logger.error("获取邀请好友链接异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 获取用户详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserInfoModel> getUserInfo(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed("获取失败,获取帐号信息为空");
			}
			userInfo.setPassword("");
			UserInfoModel result = new UserInfoModel();
			BeanUtils.copyProperties(userInfo, result);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取用户详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 用户登陆
	 * @author nada
	 * @create 2021/5/11 10:13 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserInfoModel> login(UserInfoModel userInfoModel) {
		try {
			if(userInfoModel == null){
				return ResultUtil.failed("登陆失败,登陆用户信息为空");
			}
			String loginIp = userInfoModel.getLoginIp();
			String accountNo = userInfoModel.getAccountNo();
			String password = userInfoModel.getPassword();
			if(StringUtils.isAnyBlank(accountNo,password)){
				return ResultUtil.failed("登陆失败,账号或密码不能为空");
			}
			UserInfo loginUserInfo = this.getUserInfoByAccountNo(accountNo);
			if(loginUserInfo == null){
				return ResultUtil.failed("登陆失败,获取帐号信息为空");
			}
			if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(loginUserInfo.getPassword())) {
				return ResultUtil.failed("登陆失败,帐号密码错误");
			}
			String userId = loginUserInfo.getId();
			String newToken = UUID.randomUUID().toString().replaceAll("-","");
			String newTokenKey = RedisPrefixContant.getTokenUserKey(newToken);
			String userTokenKey = RedisPrefixContant.getUserTokenKey(userId);

			//移除老得token
			String oldToken = (String) redisOpsUtil.get(userTokenKey);
			if(StringUtils.isNotEmpty(oldToken)){
				redisOpsUtil.remove(RedisPrefixContant.getTokenUserKey(oldToken));
			}
			//设置新的token
			redisOpsUtil.set(newTokenKey,userId,RedisPrefixContant.CACHE_HALF_HOUR);
			redisOpsUtil.set(userTokenKey,newToken,RedisPrefixContant.CACHE_HALF_HOUR);
			if(redisOpsUtil.get(newTokenKey)!=null){
				loginUserInfo.setPassword("");
				loginUserInfo.setToken(newToken);

				UserInfoModel result = new UserInfoModel();
				BeanUtils.copyProperties(loginUserInfo, result);
				return ResultUtil.success(result);
			}
			this.updateLoginIp(userId,loginIp);
			return ResultUtil.failed("Failed to login!");
		} catch (Exception e) {
			logger.error("用户登陆异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 用户注册
	 * @author nada
	 * @create 2021/5/11 11:19 上午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> register(UserInfoModel userInfoModel) {
		try {
			if(userInfoModel == null){
				return ResultUtil.failed("注册失败,注册信息为空");
			}
			String accountNo = userInfoModel.getAccountNo();
			String password = userInfoModel.getPassword();
			String inviteSecret = userInfoModel.getInviteSecret();
			String parentInviteCode = userInfoModel.getInviteCode();
			if(StringUtils.isAnyBlank(accountNo,password)){
				return ResultUtil.failed("注册失败,账号或密码不能为空");
			}
			if(accountNo.length() < 10 || password.length()<6 || accountNo.length() > 15 || password.length() > 20){
				return ResultUtil.failed("注册失败,账号或密码不符合要求");
			}

			UserInfo userInfo = (UserInfo)userInfoModel.clone();
			//注册账户
			synchronized (accountNo){
				UserInfo checkUserInfo = this.getUserInfoByAccountNo(accountNo);
				if(checkUserInfo !=null){
					return ResultUtil.failed("注册失败,账号已经存在");
				}
				//邀请码信息
				if(StringUtils.isNotEmpty(parentInviteCode)){
					UserInfo inviteCodeUserInfo = this.getUserInfoByInviteCode(parentInviteCode);
					if(inviteCodeUserInfo == null){
						return ResultUtil.failed("注册失败,邀请码错误");
					}
					String parentUserId = inviteCodeUserInfo.getId();
					String parentSysId = inviteCodeUserInfo.getParentSysId();
					if(CommonContact.isOkUserId(parentSysId)){
						userInfo.setParentSysId(parentSysId);
					}
					if(CommonContact.isOkUserId(parentUserId)){
						userInfo.setParent1UserId(parentUserId);
					}
				}
				//邀请码链接信息
				String parent1UserId = userInfo.getParent1UserId();
				if(StringUtils.isNotEmpty(inviteSecret) && StringUtils.isEmpty(parent1UserId)){
					try {
						String pidAndSid = HiDesUtils.desDeCode(inviteSecret);
						if(StringUtils.isNotEmpty(pidAndSid)){
							JSONObject pidAndSidJson = JSONObject.parseObject(pidAndSid);
							String parentUserId = pidAndSidJson.getString("pid");
							String parentSysId = pidAndSidJson.getString("sid");
							userInfo.setParentSysId(parentSysId);
							userInfo.setParent1UserId(parentUserId);
						}
					} catch (Exception e) {
						logger.error("邀请码链接信息解析异常",e);
					}
				}
				//当前用户信息作为上级业务员
				String parentSysId = userInfo.getParentSysId();
				if(!CommonContact.isOkUserId(parentSysId)){
					User user = UserUtils.getUser();
					if(user!=null){
						userInfo.setParentSysId(user.getId());
					}
				}
				//根据父一级ID，写入父二级ID，父三级ID
				if(CommonContact.isOkUserId(parent1UserId)){
					UserInfo parent2UserInfo = this.getUserInfoByUserId(parent1UserId);
					if(parent2UserInfo !=null && CommonContact.isOkUserId(parent2UserInfo.getId())){
						String parent2UserId = parent2UserInfo.getId();
						userInfo.setParent2UserId(parent2UserId);
						UserInfo parent3UserInfo = this.getUserInfoByUserId(parent2UserId);
						if(parent3UserInfo !=null && CommonContact.isOkUserId(parent3UserInfo.getId())){
							String parent3UserId = parent3UserInfo.getId();
							userInfo.setParent2UserId(parent3UserId);
						}
					}
				}

				//生产唯一邀请码
				Long seqId = sequenceService.getSequence();
				userInfo.setInviteCode(String.valueOf(seqId));
				UserInfo initUser = InstanceContact.initUserInfo(userInfo);
				long dbResult = userInfoDao.insert(initUser);
				if(CommonContact.dbResult(dbResult)){
					String userId = initUser.getId();
					userAccountDao.insert(InstanceContact.initUserAccount(userId));
					this.updateUserSecret(userId,parent1UserId);
					triggerApiService.registerTrigger(userId);
					return ResultUtil.success(Boolean.TRUE);
				}
				return ResultUtil.failed("注册账号失败");
			}
		} catch (Exception e) {
			logger.error("用户注册异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 获取系统配置
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> getSysConfig() {
		try {
			SysConfig sysConfig = this.getSysConfigByKey(CommonContact.SYS_KEY_COUNTDOWN_TIME);
			if(sysConfig == null){
				return ResultUtil.failed("获取失败,获取配置为空");
			}
			JSONObject configJson = new JSONObject();
			configJson.put("countDown",sysConfig.getValue());
			return ResultUtil.success(configJson);
		} catch (Exception e) {
			logger.error("获取系统配置异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}

	/**
	 * @desc 用户设置语言
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> switchLang(String token, String lang) {
		try {
			UserInfo oldUserInfo = this.getUserInfoByToken(token);
			if(oldUserInfo == null){
				return ResultUtil.failed("修改失败,获取帐号信息为空");
			}
			UserInfo updateUserInfo = new UserInfo();
			updateUserInfo.setId(oldUserInfo.getId());
			updateUserInfo.setLang(lang);
			long dbResult = userInfoDao.update(updateUserInfo);
			if(CommonContact.dbResult(dbResult)){
				this.logout(token);
				return ResultUtil.success(true);
			}
			return ResultUtil.failed("Failed to set lang!");
		} catch (Exception e) {
			logger.error("用户设置语言异常",e);
			return ResultUtil.failed(I18nCode.CODE_104);
		}
	}
}
