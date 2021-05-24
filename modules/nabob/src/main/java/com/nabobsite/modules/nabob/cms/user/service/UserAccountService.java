/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;

/**
 * 用户账户Service
 * @author face
 * @version 2021-05-24
 */
@Service
@Transactional(readOnly=true)
public class UserAccountService extends CrudService<UserAccountDao, UserAccount> {
	
	/**
	 * 获取单条数据
	 * @param userAccount
	 * @return
	 */
	@Override
	public UserAccount get(UserAccount userAccount) {
		return super.get(userAccount);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccount 查询条件
	 * @param userAccount.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccount> findPage(UserAccount userAccount) {
		return super.findPage(userAccount);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccount
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccount userAccount) {
		super.save(userAccount);
	}
	
	/**
	 * 更新状态
	 * @param userAccount
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccount userAccount) {
		super.updateStatus(userAccount);
	}
	
	/**
	 * 删除数据
	 * @param userAccount
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccount userAccount) {
		super.delete(userAccount);
	}
	
}