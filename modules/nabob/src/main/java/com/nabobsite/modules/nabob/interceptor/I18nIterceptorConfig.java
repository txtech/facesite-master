/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.interceptor;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.nabobsite.modules.nabob.config.RedisOpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 国际化拦截器
 * @author ThinkGem
 * @version 2018年1月10日
 */
@Configuration
public class I18nIterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private RedisOpsUtil redisOpsUtil;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(new I18nInterceptor(redisOpsUtil));
		String apps = Global.getProperty("web.interceptor.api.addPathPatterns");
		String epps = Global.getProperty("web.interceptor.api.excludePathPatterns");
		for (String uri : StringUtils.split(apps, ",")){
			if (StringUtils.isNotBlank(uri)){
				registration.addPathPatterns(StringUtils.trim(uri));
			}
		}
		for (String uri : StringUtils.split(epps, ",")){
			if (StringUtils.isNotBlank(uri)){
				registration.excludePathPatterns(StringUtils.trim(uri));
			}
		}
	}
}
