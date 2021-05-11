/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountBot;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountBotDao;

/**
 * 用户账户机器人信息Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserAccountBotService extends CrudService<UserAccountBotDao, UserAccountBot> {

	/**
	 * 获取单条数据
	 * @param userAccountBot
	 * @return
	 */
	@Override
	public UserAccountBot get(UserAccountBot userAccountBot) {
		return super.get(userAccountBot);
	}

	/**
	 * 查询分页数据
	 * @param userAccountBot 查询条件
	 * @param userAccountBot.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountBot> findPage(UserAccountBot userAccountBot) {
		return super.findPage(userAccountBot);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param userAccountBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountBot userAccountBot) {
		super.save(userAccountBot);
	}

	/**
	 * 更新状态
	 * @param userAccountBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountBot userAccountBot) {
		super.updateStatus(userAccountBot);
	}

	/**
	 * 删除数据
	 * @param userAccountBot
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountBot userAccountBot) {
		super.delete(userAccountBot);
	}

}
