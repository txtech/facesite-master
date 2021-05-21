/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.api.common.TriggerApiService;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.service.simple.SimpleUserService;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.base.service.SequenceService;
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.user.dao.MemberShipDao;
import com.nabobsite.modules.nabob.cms.user.entity.MemberShip;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserInfoApiService extends SimpleUserService {
	@Autowired
	private UserAccountApiService userAccountApiService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private TriggerApiService triggerApiService;
	@Autowired
	private SysApiService sysApiService;
	@Autowired
	private MemberShipDao memberShipDao;

	/**
	 * @desc 用户忘记密码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> forgetPwd(UserInfo userInfo) {
		try {
			String smsCode = userInfo.getSmsCode();
			String accountNo = userInfo.getAccountNo();
			String newPassword = userInfo.getPassword();
			if(StringUtils.isAnyEmpty(accountNo,smsCode,newPassword)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			Boolean isOk = sysApiService.verifSmsCode(accountNo,smsCode);
			if(!isOk){
				return ResultUtil.failed(I18nCode.CODE_10010);
			}
			UserInfo oldUserInfo = this.getUserInfoByAccountNo(accountNo);
			if(oldUserInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10011);
			}
			String md5NewPwd = DigestUtils.md5DigestAsHex(newPassword.getBytes());
			UserInfo updateUserInfo = new UserInfo();
			updateUserInfo.setId(oldUserInfo.getId());
			updateUserInfo.setPassword(md5NewPwd);
			long dbResult = userInfoDao.update(updateUserInfo);
			if(CommonContact.dbResult(dbResult)){
				return ResultUtil.success(true);
			}
			return ResultUtil.failed(I18nCode.CODE_10004);
		} catch (Exception e) {
			logger.error("用户忘记密码异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 用户修改密码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> updatePwd(String token,UserInfo userInfo) {
		try {
			String accountNo = userInfo.getAccountNo();
			String oldPassword = userInfo.getOldPassword();
			String newPassword = userInfo.getPassword();
			if(StringUtils.isAnyEmpty(accountNo,oldPassword,newPassword)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			UserInfo oldUserInfo = this.getUserInfoByToken(token);
			if(oldUserInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			if (oldPassword.equalsIgnoreCase(newPassword)) {
				return ResultUtil.failed(I18nCode.CODE_10012);
			}
			String oldDbPwd = oldUserInfo.getPassword();
			String md5OldPwd = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
			if (!oldDbPwd.equalsIgnoreCase(md5OldPwd)) {
				return ResultUtil.failed(I18nCode.CODE_10013);
			}
			if(!accountNo.equalsIgnoreCase(oldUserInfo.getAccountNo())){
				return ResultUtil.failed(I18nCode.CODE_10014);
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
			return ResultUtil.failed(I18nCode.CODE_10004);
		} catch (Exception e) {
			logger.error("用户修改密码异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
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
			return ResultUtil.success(true);
		} catch (Exception e) {
			logger.error("用户退出异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取邀请好友链接
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> shareFriends(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("pid",userInfo.getId());
			jsonObject.put("sid",userInfo.getParentSysId());
			String registerUrl = "param_parent="+ HiDesUtils.desEnCode(jsonObject.toString());
			JSONObject result = new JSONObject();
			result.put("shareUrl",registerUrl);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取邀请好友链接异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
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
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			return ResultUtil.success(userInfo);
		} catch (Exception e) {
			logger.error("获取用户详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取用户详情
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<UserInfo>> getUserDirectTeamList(String token) {
		try {
			UserInfo userInfo = this.getUserInfoByToken(token);
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10005);
			}
			UserInfo parms = new UserInfo();
			parms.setParent1UserId(userInfo.getId());
			List<UserInfo> userInfoList = userInfoDao.findList(parms);
			return ResultUtil.success(userInfoList);
		} catch (Exception e) {
			logger.error("获取用户详情异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 用户登陆
	 * @author nada
	 * @create 2021/5/11 10:13 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<JSONObject> login(UserInfo userInfo) {
		try {
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			String loginIp = userInfo.getLoginIp();
			String accountNo = userInfo.getAccountNo();
			String password = userInfo.getPassword();
			String imgCodeKey = userInfo.getCodeKey();
			String imgCode = userInfo.getImgCode();
			if(StringUtils.isAnyBlank(accountNo,password)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			if(StringUtils.isNotEmpty(imgCodeKey) && StringUtils.isNotEmpty(imgCode)){
				Boolean isOk = sysApiService.verifImgRandomCode(imgCodeKey,imgCode);
				if(!isOk){
					return ResultUtil.failed(I18nCode.CODE_10010);
				}
			}
			UserInfo loginUserInfo = this.getUserInfoByAccountNo(accountNo);
			if(loginUserInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10011);
			}
			if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(loginUserInfo.getPassword())) {
				return ResultUtil.failed(I18nCode.CODE_10015);
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
				this.updateLoginIp(userId,loginIp);
				JSONObject result = new JSONObject();
				result.put("token",newToken);
				return ResultUtil.success(result);
			}
			return ResultUtil.failed(I18nCode.CODE_10004);
		} catch (Exception e) {
			logger.error("用户登陆异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 用户注册
	 * @author nada
	 * @create 2021/5/11 11:19 上午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> register(UserInfo userInfo) {
		try {
			if(userInfo == null){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			String accountNo = userInfo.getAccountNo();
			String password = userInfo.getPassword();
			String inviteSecret = userInfo.getInviteSecret();
			String parentInviteCode = userInfo.getInviteCode();
			if(StringUtils.isAnyBlank(accountNo,password)){
				return ResultUtil.failed(I18nCode.CODE_10007);
			}
			if(accountNo.length() < 10 || accountNo.length() > 20 || password.length()<6  || password.length() > 20){
				return ResultUtil.failed(I18nCode.CODE_10016);
			}

			synchronized (accountNo){
				UserInfo checkUserInfo = this.getUserInfoByAccountNo(accountNo);
				if(checkUserInfo !=null){
					return ResultUtil.failed(I18nCode.CODE_10017);
				}
				//邀请码信息
				if(StringUtils.isNotEmpty(parentInviteCode)){
					UserInfo inviteCodeUserInfo = this.getUserInfoByInviteCode(parentInviteCode);
					if(inviteCodeUserInfo == null){
						return ResultUtil.failed(I18nCode.CODE_10018);
					}
					String parent1UserId = inviteCodeUserInfo.getId();
					String parentSysId = inviteCodeUserInfo.getParentSysId();
					if(CommonContact.isOkUserId(parentSysId)){
						userInfo.setParentSysId(parentSysId);
					}
					if(CommonContact.isOkUserId(parent1UserId)){
						userInfo.setParent1UserId(parent1UserId);
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
						logger.error("邀请码链接信息解析异常,{},{}",accountNo,inviteSecret,e);
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
					UserInfo parent1UserInfo = this.getUserInfoByUserId(parent1UserId);
					if(parent1UserInfo !=null){
						String parent2UserId = parent1UserInfo.getParent1UserId();
						userInfo.setParent2UserId(parent2UserId);
						UserInfo parent2UserInfo = this.getUserInfoByUserId(parent2UserId);
						if(parent2UserInfo !=null){
							String parent3UserId = parent2UserInfo.getParent1UserId();
							userInfo.setParent3UserId(parent3UserId);
						}
					}
				}

				//生产唯一邀请码
				Long seqId = sequenceService.getSequence();
				userInfo.setInviteCode(String.valueOf(seqId));
				UserInfo initUser = InstanceContact.initUserInfo(userInfo);
				long dbResult = userInfoDao.insert(initUser);
				if(!CommonContact.dbResult(dbResult)){
					logger.error("注册用户失败,保存用户失败");
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				//修改邀请秘文
				String userId = initUser.getId();
				this.updateUserSecret(userId,parent1UserId);
				//初始化账户
				dbResult = userAccountDao.insert(InstanceContact.initUserAccount(userId));
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				//初始化云仓库账户
				dbResult = userAccountWarehouseDao.insert(InstanceContact.initUserAccountWarehouse(userId));
				if(!CommonContact.dbResult(dbResult)){
					return ResultUtil.failed(I18nCode.CODE_10004);
				}
				//注册新用户送奖励
				int type = CommonContact.USER_ACCOUNT_DETAIL_TYPE_2;
				Boolean isOk = userAccountApiService.updateAccountBalance(userId,type,LogicStaticContact.USER_REGISTER_REWARD,userId,CommonContact.USER_ACCOUNT_DETAIL_TITLE_2);
				if(!isOk){
					logger.error("注册用户成功,用户送奖励失败,{}",userId);
				}
				triggerApiService.registerTrigger(userId);
				return ResultUtil.success(true);
			}
		} catch (Exception e) {
			logger.error("用户注册异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取会员资格
	 * @author nada
	 * @create 2021/5/19 9:25 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<MemberShip> getMemberShipInfo(String id) {
		try {
			MemberShip memberShip = new MemberShip();
			memberShip.setId(id);
			MemberShip result = memberShipDao.getByEntity(memberShip);
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("获取会员资格异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 获取会员资格
	 * @author nada
	 * @create 2021/5/19 9:25 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<List<MemberShip>> getMemberShip() {
		try {
			List<MemberShip> shipList = memberShipDao.findList(new MemberShip());
			return ResultUtil.success(shipList);
		} catch (Exception e) {
			logger.error("获取系统配置异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
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
			JSONObject configJson = new JSONObject();
			List<SysConfig> sysConfigList = this.getSysConfigList();
			for (SysConfig sysConfig : sysConfigList) {
				String key = sysConfig.getKey();
				String value = sysConfig.getValue();
				if(StringUtils.isAnyBlank(key,value)){
					continue;
				}
				if(key.equalsIgnoreCase(CommonContact.SYS_KEY_COUNTDOWN_TIME)){
					configJson.put("countDownTime",value);
					continue;
				}
				if(key.equalsIgnoreCase(CommonContact.SYS_KEY_CURRENT_VERSION)){
					configJson.put("appCurrentVersion",value);
					continue;
				}
				if(key.equalsIgnoreCase(CommonContact.SYS_KEY_UPDATE_VERSION)){
					configJson.put("appUpdateVersion",value);
					continue;
				}
				if(key.equalsIgnoreCase(CommonContact.SYS_KEY_APP_DOWNLOAD_URL)){
					configJson.put("appDownloadUrl",value);
					continue;
				}
			}
			return ResultUtil.success(configJson);
		} catch (Exception e) {
			logger.error("获取系统配置异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}
}
