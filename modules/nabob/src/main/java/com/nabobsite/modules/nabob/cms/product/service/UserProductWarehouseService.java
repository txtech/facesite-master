/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductWarehouseDao;

/**
 * 用户产品仓库信息Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserProductWarehouseService extends CrudService<UserProductWarehouseDao, UserProductWarehouse> {

	/**
	 * 获取单条数据
	 * @param userProductWarehouse
	 * @return
	 */
	@Override
	public UserProductWarehouse get(UserProductWarehouse userProductWarehouse) {
		return super.get(userProductWarehouse);
	}

	/**
	 * 查询分页数据
	 * @param userProductWarehouse 查询条件
	 * @param userProductWarehouse.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserProductWarehouse> findPage(UserProductWarehouse userProductWarehouse) {
		return super.findPage(userProductWarehouse);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param userProductWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserProductWarehouse userProductWarehouse) {
		super.save(userProductWarehouse);
	}

	/**
	 * 更新状态
	 * @param userProductWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserProductWarehouse userProductWarehouse) {
		super.updateStatus(userProductWarehouse);
	}

	/**
	 * 删除数据
	 * @param userProductWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserProductWarehouse userProductWarehouse) {
		super.delete(userProductWarehouse);
	}

}
