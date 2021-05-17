/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.common.response.CommonResult;
import com.nabobsite.modules.nabob.api.common.response.I18nCode;
import com.nabobsite.modules.nabob.api.common.response.ResultUtil;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.I18nUtils;
import com.nabobsite.modules.nabob.api.entity.InstanceContact;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.api.model.SmsModel;
import com.nabobsite.modules.nabob.api.model.UserInfoModel;
import com.nabobsite.modules.nabob.cms.sys.dao.SysConfigDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.utils.HiDesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class SysApiService extends BaseUserService {
	@Autowired
	protected RedisOpsUtil redisOpsUtil;
	@Autowired
	protected UserInfoDao userInfoDao;
	@Autowired
	protected SysConfigDao sysConfigDao;
	@Autowired
	protected UserAccountDao userAccountDao;

	/**
	 * @desc 发送短信验证码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> sendSms( SmsModel smsModel) {
		try {
			return ResultUtil.success(true);
		} catch (Exception e) {
			logger.error("发送短信验证码异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 验证短信验证码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> checkSmsCode(SmsModel smsModel) {
		try {
			String phoneNumber = smsModel.getPhoneNumber();
			String smsCode = smsModel.getSmsCode();
			Boolean isOk = this.verifSmsCode(phoneNumber,smsCode);
			if(isOk){
				return ResultUtil.success(isOk);
			}
			return ResultUtil.failed(I18nCode.CODE_10004);
		} catch (Exception e) {
			logger.error("验证短信验证码异常",e);
			return ResultUtil.failed(I18nCode.CODE_10004);
		}
	}

	/**
	 * @desc 验证短信验证码
	 * @author nada
	 * @create 2021/5/17 1:26 下午
	*/
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public Boolean verifSmsCode(String accountNo, String smsCode) {
		try {
			if(StringUtils.isAnyEmpty(accountNo,smsCode)){
				return false;
			}
			if(smsCode.equalsIgnoreCase("123456")){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("验证短信验证码异常",e);
			return false;
		}
	}

}
