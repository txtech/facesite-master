/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBot;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductBotDao;

/**
 * 用户产品机器人信息Service
 * @author face
 * @version 2021-05-17
 */
@Service
@Transactional(readOnly=true)
public class UserProductBotService extends CrudService<UserProductBotDao, UserProductBot> {
	
	/**
	 * 获取单条数据
	 * @param userProductBot
	 * @return
	 */
	@Override
	public UserProductBot get(UserProductBot userProductBot) {
		return super.get(userProductBot);
	}
	
	/**
	 * 查询分页数据
	 * @param userProductBot 查询条件
	 * @param userProductBot.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserProductBot> findPage(UserProductBot userProductBot) {
		return super.findPage(userProductBot);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userProductBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserProductBot userProductBot) {
		super.save(userProductBot);
	}
	
	/**
	 * 更新状态
	 * @param userProductBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserProductBot userProductBot) {
		super.updateStatus(userProductBot);
	}
	
	/**
	 * 删除数据
	 * @param userProductBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserProductBot userProductBot) {
		super.delete(userProductBot);
	}
	
}