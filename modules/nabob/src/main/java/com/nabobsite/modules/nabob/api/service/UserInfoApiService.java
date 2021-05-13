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
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.entity.DbInstanceEntity;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.base.service.SequenceService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
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
	@Autowired
	private UserAccountApiService userAccountApiService;
	@Autowired
	private TriggerApiService triggerApiService;

	/**
	 * @desc 用户修改密码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> updatePwd(String accountNo, String oldPassword,String newPassword,String token) {
		try {
			if(StringUtils.isEmpty(token)){
				return ResultUtil.failed("修改失败,获取令牌为空");
			}
			if(StringUtils.isAnyEmpty(accountNo,oldPassword,newPassword)){
				return ResultUtil.failed("修改失败,修改信息为空");
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return ResultUtil.failed("获取失败,登陆令牌失效");
			}
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
			if(CommonStaticContact.dbResult(dbResult)){
				this.logout(token);
				return ResultUtil.success(true);
			}
			return ResultUtil.failed("Failed to update password!");
		} catch (Exception e) {
			logger.error("Failed to update password!",e);
			return ResultUtil.failed("Failed to update password!");
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
			logger.error("Failed to logout user!",e);
			return ResultUtil.failed("Failed to logout user!");
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
			if(StringUtils.isEmpty(token)){
				return ResultUtil.failed("获取失败,获取令牌为空");
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return ResultUtil.failed("获取失败,登陆令牌失效");
			}
			UserInfo userInfo = this.getUserInfoByUserId(userId);
			if(userInfo == null){
				return ResultUtil.failed("获取失败,获取帐号信息为空");
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("pid",userInfo.getId());
			jsonObject.put("sid",userInfo.getParentSysId());
			String registerUrl = "http://localhost?param_parent="+ HiDesUtils.desEnCode(jsonObject.toString());
			return ResultUtil.success(registerUrl);
		} catch (Exception e) {
			logger.error("Failed to get share friends url!",e);
			return ResultUtil.failed("Failed to get share friends url!");
		}
	}

	/**
	 * @desc 获取用户详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserInfo> getUserInfo(String token) {
		try {
			if(StringUtils.isEmpty(token)){
				return ResultUtil.failed("获取失败,获取令牌为空");
			}
			String userId = (String) redisOpsUtil.get(RedisPrefixContant.getTokenUserKey(token));
			if(StringUtils.isEmpty(userId)){
				return ResultUtil.failed("获取失败,登陆令牌失效");
			}
			UserInfo userInfo = this.getUserInfoByUserId(userId);
			if(userInfo == null){
				return ResultUtil.failed("获取失败,获取帐号信息为空");
			}
			return ResultUtil.success(userInfo);
		} catch (Exception e) {
			logger.error("Failed to get userinfo!",e);
			return ResultUtil.failed("Failed to get userinfo!");
		}
	}

	/**
	 * @desc 用户登陆
	 * @author nada
	 * @create 2021/5/11 10:13 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<UserInfo> login(UserInfo userInfo,String param_lang) {
		try {
			if(userInfo == null){
				return ResultUtil.failed("登陆失败,登陆用户信息为空");
			}
			String accountNo = userInfo.getAccountNo();
			String password = userInfo.getPassword();
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
				return ResultUtil.success(loginUserInfo);
			}
			return ResultUtil.failed("Failed to login!");
		} catch (Exception e) {
			logger.error("Failed to login!",e);
			return ResultUtil.failed("Failed to login!");
		}
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

			String accountNo = userInfo.getAccountNo();
			String password = userInfo.getPassword();
			if(StringUtils.isAnyBlank(accountNo,password)){
				return ResultUtil.failed("注册失败,账号或密码不能为空");
			}
			if(accountNo.length() < 10 || password.length()<6 || accountNo.length() > 15 || password.length() > 20){
				return ResultUtil.failed("注册失败,账号或密码不符合要求");
			}
			String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
			userInfo.setAccountNo(accountNo);
			userInfo.setPassword(md5Pass);

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
				UserInfo initUser = DbInstanceEntity.initUserInfo(userInfo);
				long dbResult = userInfoDao.insert(initUser);
				if(CommonStaticContact.dbResult(dbResult)){
					String userId = initUser.getId();
					userAccountApiService.save(DbInstanceEntity.initUserAccount(userId));
					triggerApiService.registerTrigger(userId);
					return ResultUtil.success(Boolean.TRUE);
				}
				return ResultUtil.failed("注册账号失败");
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
		try {
			if(StringUtils.isEmpty(accountNo)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setAccountNo(accountNo);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			logger.error("根据账号获取用户信息异常",e);
			return null;
		}
	}

	/**
	 * @desc 根据邀请码获取用户信息
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
			logger.error("根据邀请码获取用户信息异常",e);
			return null;
		}
	}

	/**
	 * @desc 根据账号ID获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			logger.error("根据账号ID获取用户信息异常",e);
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
