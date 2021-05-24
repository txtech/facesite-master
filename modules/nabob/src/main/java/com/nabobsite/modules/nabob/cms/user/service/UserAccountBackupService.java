/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountBackup;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountBackupDao;

/**
 * 用户账户备份Service
 * @author face
 * @version 2021-05-24
 */
@Service
@Transactional(readOnly=true)
public class UserAccountBackupService extends CrudService<UserAccountBackupDao, UserAccountBackup> {
	
	/**
	 * 获取单条数据
	 * @param userAccountBackup
	 * @return
	 */
	@Override
	public UserAccountBackup get(UserAccountBackup userAccountBackup) {
		return super.get(userAccountBackup);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountBackup 查询条件
	 * @param userAccountBackup.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountBackup> findPage(UserAccountBackup userAccountBackup) {
		return super.findPage(userAccountBackup);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountBackup
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountBackup userAccountBackup) {
		super.save(userAccountBackup);
	}
	
	/**
	 * 更新状态
	 * @param userAccountBackup
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountBackup userAccountBackup) {
		super.updateStatus(userAccountBackup);
	}
	
	/**
	 * 删除数据
	 * @param userAccountBackup
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountBackup userAccountBackup) {
		super.delete(userAccountBackup);
	}
	
}