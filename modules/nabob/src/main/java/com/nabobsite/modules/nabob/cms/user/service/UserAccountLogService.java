/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountLog;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountLogDao;

/**
 * 用户账户Service
 * @author face
 * @version 2021-05-14
 */
@Service
@Transactional(readOnly=true)
public class UserAccountLogService extends CrudService<UserAccountLogDao, UserAccountLog> {
	
	/**
	 * 获取单条数据
	 * @param userAccountLog
	 * @return
	 */
	@Override
	public UserAccountLog get(UserAccountLog userAccountLog) {
		return super.get(userAccountLog);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountLog 查询条件
	 * @param userAccountLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountLog> findPage(UserAccountLog userAccountLog) {
		return super.findPage(userAccountLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountLog userAccountLog) {
		super.save(userAccountLog);
	}
	
	/**
	 * 更新状态
	 * @param userAccountLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountLog userAccountLog) {
		super.updateStatus(userAccountLog);
	}
	
	/**
	 * 删除数据
	 * @param userAccountLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountLog userAccountLog) {
		super.delete(userAccountLog);
	}
	
}