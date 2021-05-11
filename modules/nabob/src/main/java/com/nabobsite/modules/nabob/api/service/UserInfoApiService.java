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
import com.nabobsite.modules.nabob.cms.base.service.SequenceService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.service.UserInfoService;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserInfoApiService extends CrudService<UserInfoDao, UserInfo> {

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private SequenceService sequenceService;

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
				if(StringUtils.isEmpty(accountNoEntry) || StringUtils.isEmpty(passwordEntry)){
					return ResultUtil.failed("注册失败,账号或密码不能为空");
				}
				accountNo = DesUtils.decode(accountNoEntry, secretKey);
				password = DesUtils.decode(passwordEntry, secretKey);
				userInfo.setAccountNo(accountNo);
				userInfo.setPassword(password);
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
	 * 获取单条数据
	 * @param userInfo
	 * @return
	 */
	@Override
	public UserInfo get(UserInfo userInfo) {
		return super.get(userInfo);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param userInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserInfo userInfo) {
		super.save(userInfo);
	}

	/**
	 * 更新状态
	 * @param userInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserInfo userInfo) {
		super.updateStatus(userInfo);
	}
}
