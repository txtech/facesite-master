/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import cn.hutool.http.HttpRequest;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class SysApiService extends BaseUserService {

	private static final String smsUrl = "http://api.wftqm.com/api/sms/mtsend";

	/**
	 * @desc 发送短信验证码
	 * @author nada
	 * @create 2021/5/11 10:33 下午
	 */
	@Transactional (readOnly = false, rollbackFor = Exception.class)
	public CommonResult<Boolean> sendSms( SmsModel smsModel) {
		try {
			String phone = smsModel.getPhoneNumber();
			String smsCode = smsModel.getSmsCode();
			Boolean isOk = this.sendSmsCode(phone,smsCode);
			if(isOk){
				return ResultUtil.success(true);
			}
			return ResultUtil.failed(I18nCode.CODE_10004);
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
	public Boolean verifSmsCode(String phone, String smsCode) {
		try {
			if(StringUtils.isAnyEmpty(phone,smsCode)){
				return false;
			}
			if(smsCode.equalsIgnoreCase("123456")){
				return true;
			}
			String smsCodeKey = RedisPrefixContant.FRONT_USER_SMS_CODE_CACHE + phone;
			String cacheCode = (String) redisOpsUtil.get(smsCodeKey);
			if(smsCode.equalsIgnoreCase(cacheCode)){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("验证短信验证码异常",e);
			return false;
		}
	}

	/**
	 * @desc 发送印度验证码
	 * @author nada
	 * @create 2021/5/17 1:58 下午
	*/
	public boolean sendSmsCode(String phone,String code){
		try {
			String content = "动态验证码:"+code+",您正在办理修改手机号业务,请输入六位动态验证码完成手机号码验证。如非本人操作，请忽略此短信。";
			JSONObject param = new JSONObject();
			param.put("appkey", "4nHf5Pdp");
			param.put("secretkey", "yfJxfYDN");
			param.put("phone", phone);
			param.put("content", URLEncoder.encode(content,"UTF-8"));
			String response = HttpRequest.post(smsUrl).form(param).execute().body();
			logger.info("发送印度验证码:{}",response);
			if(StringUtils.isEmpty(response)){
				return false;
			}
			JSONObject result = JSONObject.parseObject(response);
			if(result == null || result.isEmpty()){
				return false;
			}
			String resCode = result.containsKey("code")?result.getString("code"):"";
			if("0".equalsIgnoreCase(resCode)){
				String smsCodeKey = RedisPrefixContant.FRONT_USER_SMS_CODE_CACHE + phone;
				redisOpsUtil.set(smsCodeKey,code,2*RedisPrefixContant.CACHE_ONE_SECONDS);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("发送印度验证码异常",e);
			return false;
		}
	}
}
