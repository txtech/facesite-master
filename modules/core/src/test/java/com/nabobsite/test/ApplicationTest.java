/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * JeeSite Web
 * @author ThinkGem
 * @version 2018-1-8
 */
@ActiveProfiles("test")
@SpringBootApplication
public class ApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class, args);
	}

}
