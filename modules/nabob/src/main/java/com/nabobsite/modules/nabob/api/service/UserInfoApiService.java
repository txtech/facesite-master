/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.entity.DbInstanceEntity;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.base.service.SequenceService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.service.UserInfoService;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserInfoApiService extends CrudService<UserInfoDao, UserInfo> {

	@Autowired
	private RedisOpsUtil redisOpsUtil;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private SequenceService sequenceService;


	/**
	 * @desc 用户修改密码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> updatePwd(String accountNo, String oldPassword,String newPassword,String token) {
		if(StringUtils.isEmpty(token)){
			return ResultUtil.failed("修改失败,获取令牌为空");
		}
		if(StringUtils.isAnyEmpty(accountNo,oldPassword,newPassword)){
			return ResultUtil.failed("修改失败,修改信息为空");
		}
		String userId = (String) redisOpsUtil.get(RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+token);
		UserInfo oldUserInfo = this.getUserInfoByUserId(userId);
		if(oldUserInfo == null){
			return ResultUtil.failed("修改失败,获取帐号信息为空");
		}
		if (oldPassword.equalsIgnoreCase(newPassword)) {
			return ResultUtil.failed("修改失败,新旧密码不能一样");
		}
		String oldDbPwd = oldUserInfo.getPassword();
		String md5OldPwd = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
		if (!oldDbPwd.equalsIgnoreCase(md5OldPwd)) {
			return ResultUtil.failed("旧密码输入错误");
		}

		String md5NewPwd = DigestUtils.md5DigestAsHex(newPassword.getBytes());
		UserInfo updateUserInfo = new UserInfo();
		updateUserInfo.setId(oldUserInfo.getId());
		updateUserInfo.setPassword(md5NewPwd);
		userInfoDao.update(updateUserInfo);
		return ResultUtil.success(true);
	}

	/**
	 * @desc 用户退出
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> logout(String token) {
		redisOpsUtil.remove(RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+token);
		return ResultUtil.success(Boolean.TRUE);
	}

	/**
	 * @desc 获取邀请好友链接
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<String> shareFriends(String token) {
		if(StringUtils.isEmpty(token)){
			return ResultUtil.failed("获取失败,获取令牌为空");
		}
		String userId = (String) redisOpsUtil.get(RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+token);
		UserInfo userInfo = this.getUserInfoByUserId(userId);
		if(userInfo == null){
			return ResultUtil.failed("获取失败,获取帐号信息为空");
		}
		String registerUrl = "http://c-mart.phlife.phshare?pid="+ HiDesUtils.desEnCode(userId);
		return ResultUtil.success(registerUrl);
	}

	/**
	 * @desc 获取用户详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserInfo> getUserInfo(String token) {
		if(StringUtils.isEmpty(token)){
			return ResultUtil.failed("获取失败,获取令牌为空");
		}
		String userId = (String) redisOpsUtil.get(RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+token);
		UserInfo userInfo = this.getUserInfoByUserId(userId);
		if(userInfo == null){
			return ResultUtil.failed("获取失败,获取帐号信息为空");
		}
		return ResultUtil.success(userInfo);
	}

	/**
	 * @desc 用户登陆
	 * @author nada
	 * @create 2021/5/11 10:13 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserInfo> login(UserInfo userInfo,String param_lang,String param_deviceType,String loginIp) {
		if(userInfo == null){
			return ResultUtil.failed("登陆失败,登陆用户信息为空");
		}
		//解密账户密码
		String secretKey = Global.getConfig("shiro.loginSubmit.secretKey");
		String accountNo = null;
		String password = null;
		try {
			String accountNoEntry = userInfo.getAccountNo();
			String passwordEntry = userInfo.getPassword();
			if(StringUtils.isAnyBlank(accountNoEntry,passwordEntry)){
				return ResultUtil.failed("登陆失败,账号或密码不能为空");
			}
			accountNo = DesUtils.decode(accountNoEntry, secretKey);
			password = DesUtils.decode(passwordEntry, secretKey);
			String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
			userInfo.setAccountNo(accountNo);
			userInfo.setPassword(md5Pass);
		} catch (Exception e) {
			logger.error("Failed to decrypt accountNo or password!",e);
		}

		UserInfo loginUserInfo = this.getUserInfoByAccountNo(accountNo);
		if(loginUserInfo == null){
			return ResultUtil.failed("登陆失败,获取帐号信息为空");
		}
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(loginUserInfo.getPassword())) {
			return ResultUtil.failed("登陆失败,帐号密码错误");
		}
		String userId = loginUserInfo.getId();
		String token = UUID.randomUUID().toString();
		loginUserInfo.setPassword("");
		loginUserInfo.setToken(token);
		redisOpsUtil.set(RedisPrefixContant.FRONT_USER_TOKEN_INFO_CACHE+token,userId,RedisPrefixContant.CACHE_HALF_HOUR);
		return ResultUtil.success(loginUserInfo);
	}

	/**
	 * @desc 用户注册
	 * @author nada
	 * @create 2021/5/11 11:19 上午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> register(UserInfo userInfo,String param_parent) {
		try {
			if(userInfo == null){
				return ResultUtil.failed("注册失败,注册信息为空");
			}

			//解密账户密码
			String secretKey = Global.getConfig("shiro.loginSubmit.secretKey");
			String accountNo = null;
			String password = null;
			try {
				String accountNoEntry = userInfo.getAccountNo();
				String passwordEntry = userInfo.getPassword();
				if(StringUtils.isAnyBlank(accountNoEntry,passwordEntry)){
					return ResultUtil.failed("注册失败,账号或密码不能为空");
				}
				accountNo = DesUtils.decode(accountNoEntry, secretKey);
				password = DesUtils.decode(passwordEntry, secretKey);
				String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
				userInfo.setAccountNo(accountNo);
				userInfo.setPassword(md5Pass);
			} catch (Exception e) {
				logger.error("Failed to decrypt accountNo or password!",e);
			}
			if(StringUtils.isEmpty(accountNo) || StringUtils.isEmpty(password)){
				return ResultUtil.failed("注册失败,账号或密码解析失败");
			}
			if(accountNo.length() < 10 || password.length()<6 || accountNo.length() > 15 || password.length() > 20){
				return ResultUtil.failed("注册失败,账号或密码不符合要求");
			}

			//注册账户
			synchronized (accountNo){
				UserInfo checkUserInfo = this.getUserInfoByAccountNo(accountNo);
				if(checkUserInfo !=null){
					return ResultUtil.failed("注册失败,账号已经存在");
				}
				//邀请码信息
				String parentInviteCode = userInfo.getInviteCode();
				if(StringUtils.isNotEmpty(parentInviteCode)){
					UserInfo inviteCodeUserInfo = this.getUserInfoByInviteCode(parentInviteCode);
					if(inviteCodeUserInfo == null){
						return ResultUtil.failed("注册失败,邀请码错误");
					}
					String parentUserId = userInfo.getId();
					String parentSysId = userInfo.getParentSysId();
					if(StringUtils.isNotEmpty(parentUserId) && StringUtils.isNotEmpty(parentSysId)){
						userInfo.setParentSysId(parentSysId);
						userInfo.setParentUserId(parentUserId);
					}
				}
				//邀请码链接信息
				if(StringUtils.isNotEmpty(param_parent) && StringUtils.isEmpty(userInfo.getParentUserId())){
					try {
						String pidAndSid = HiDesUtils.desDeCode(param_parent);
						if(StringUtils.isNotEmpty(pidAndSid)){
							JSONObject pidAndSidJson = JSONObject.parseObject(pidAndSid);
							String parentUserId = pidAndSidJson.getString("pid");
							String parentSysId = pidAndSidJson.getString("sid");
							userInfo.setParentSysId(parentSysId);
							userInfo.setParentUserId(parentUserId);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//当前用户信息作为上级业务员
				if(StringUtils.isEmpty(userInfo.getParentSysId()) || "0".equalsIgnoreCase(userInfo.getParentSysId())){
					User user = UserUtils.getUser();
					if(user!=null){
						userInfo.setParentSysId(user.getId());
					}
				}

				//生产唯一邀请码
				Long seqId = sequenceService.getSequence();
				userInfo.setInviteCode(String.valueOf(seqId));
				long userId = userInfoDao.insert(DbInstanceEntity.initUserInfo(userInfo));
				if(userId < 0){
					return ResultUtil.failed("注册账号失败");
				}
				return ResultUtil.success(Boolean.TRUE);
			}
		} catch (Exception e) {
			logger.error("Failed to register user!",e);
			return ResultUtil.failed("Failed to register user!");
		}
	}

	/**
	 * @desc 根据账号获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	*/
	public UserInfo getUserInfoByAccountNo(String accountNo) {
		UserInfo userInfo = new UserInfo();
		userInfo.setAccountNo(accountNo);
		return userInfoDao.getByEntity(userInfo);
	}

	/**
	 * @desc 根据邀请码获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByInviteCode(String inviteCode) {
		UserInfo userInfo = new UserInfo();
		userInfo.setInviteCode(inviteCode);
		return userInfoDao.getByEntity(userInfo);
	}

	/**
	 * @desc 根据账号ID获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		return userInfoDao.getByEntity(userInfo);
	}
}
