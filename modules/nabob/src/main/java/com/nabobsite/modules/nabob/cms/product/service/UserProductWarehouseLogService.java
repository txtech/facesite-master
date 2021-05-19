/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseLog;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductWarehouseLogDao;

/**
 * 用户产品仓库日志Service
 * @author face
 * @version 2021-05-19
 */
@Service
@Transactional(readOnly=true)
public class UserProductWarehouseLogService extends CrudService<UserProductWarehouseLogDao, UserProductWarehouseLog> {
	
	/**
	 * 获取单条数据
	 * @param userProductWarehouseLog
	 * @return
	 */
	@Override
	public UserProductWarehouseLog get(UserProductWarehouseLog userProductWarehouseLog) {
		return super.get(userProductWarehouseLog);
	}
	
	/**
	 * 查询分页数据
	 * @param userProductWarehouseLog 查询条件
	 * @param userProductWarehouseLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserProductWarehouseLog> findPage(UserProductWarehouseLog userProductWarehouseLog) {
		return super.findPage(userProductWarehouseLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userProductWarehouseLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserProductWarehouseLog userProductWarehouseLog) {
		super.save(userProductWarehouseLog);
	}
	
	/**
	 * 更新状态
	 * @param userProductWarehouseLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserProductWarehouseLog userProductWarehouseLog) {
		super.updateStatus(userProductWarehouseLog);
	}
	
	/**
	 * 删除数据
	 * @param userProductWarehouseLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserProductWarehouseLog userProductWarehouseLog) {
		super.delete(userProductWarehouseLog);
	}
	
}