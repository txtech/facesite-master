/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBotLog;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductBotLogDao;

/**
 * 用户产品机器人记录Service
 * @author face
 * @version 2021-05-17
 */
@Service
@Transactional(readOnly=true)
public class UserProductBotLogService extends CrudService<UserProductBotLogDao, UserProductBotLog> {
	
	/**
	 * 获取单条数据
	 * @param userProductBotLog
	 * @return
	 */
	@Override
	public UserProductBotLog get(UserProductBotLog userProductBotLog) {
		return super.get(userProductBotLog);
	}
	
	/**
	 * 查询分页数据
	 * @param userProductBotLog 查询条件
	 * @param userProductBotLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserProductBotLog> findPage(UserProductBotLog userProductBotLog) {
		return super.findPage(userProductBotLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userProductBotLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserProductBotLog userProductBotLog) {
		super.save(userProductBotLog);
	}
	
	/**
	 * 更新状态
	 * @param userProductBotLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserProductBotLog userProductBotLog) {
		super.updateStatus(userProductBotLog);
	}
	
	/**
	 * 删除数据
	 * @param userProductBotLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserProductBotLog userProductBotLog) {
		super.delete(userProductBotLog);
	}
	
}