/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountWarehouseDao;

/**
 * 用户账户仓库信息Service
 * @author face
 * @version 2021-05-24
 */
@Service
@Transactional(readOnly=true)
public class UserAccountWarehouseService extends CrudService<UserAccountWarehouseDao, UserAccountWarehouse> {
	
	/**
	 * 获取单条数据
	 * @param userAccountWarehouse
	 * @return
	 */
	@Override
	public UserAccountWarehouse get(UserAccountWarehouse userAccountWarehouse) {
		return super.get(userAccountWarehouse);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountWarehouse 查询条件
	 * @param userAccountWarehouse.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountWarehouse> findPage(UserAccountWarehouse userAccountWarehouse) {
		return super.findPage(userAccountWarehouse);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountWarehouse userAccountWarehouse) {
		super.save(userAccountWarehouse);
	}
	
	/**
	 * 更新状态
	 * @param userAccountWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountWarehouse userAccountWarehouse) {
		super.updateStatus(userAccountWarehouse);
	}
	
	/**
	 * 删除数据
	 * @param userAccountWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountWarehouse userAccountWarehouse) {
		super.delete(userAccountWarehouse);
	}
	
}