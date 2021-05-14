/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.jeesite.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

/**
 * Application
 * @author ThinkGem
 * @version 2018-10-13
 */
@SpringBootApplication(scanBasePackages={"com.jeesite.modules","com.nabobsite.modules.nabob"})
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 错误页面有容器来处理，而不是SpringBoot
		this.setRegisterErrorPageFilter(false);
		return builder.sources(Application.class);
	}
}
