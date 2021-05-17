/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseRecord;
import com.nabobsite.modules.nabob.cms.product.dao.UserProductWarehouseRecordDao;

/**
 * 用户产品仓库日志Service
 * @author face
 * @version 2021-05-17
 */
@Service
@Transactional(readOnly=true)
public class UserProductWarehouseRecordService extends CrudService<UserProductWarehouseRecordDao, UserProductWarehouseRecord> {
	
	/**
	 * 获取单条数据
	 * @param userProductWarehouseRecord
	 * @return
	 */
	@Override
	public UserProductWarehouseRecord get(UserProductWarehouseRecord userProductWarehouseRecord) {
		return super.get(userProductWarehouseRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param userProductWarehouseRecord 查询条件
	 * @param userProductWarehouseRecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserProductWarehouseRecord> findPage(UserProductWarehouseRecord userProductWarehouseRecord) {
		return super.findPage(userProductWarehouseRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userProductWarehouseRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserProductWarehouseRecord userProductWarehouseRecord) {
		super.save(userProductWarehouseRecord);
	}
	
	/**
	 * 更新状态
	 * @param userProductWarehouseRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserProductWarehouseRecord userProductWarehouseRecord) {
		super.updateStatus(userProductWarehouseRecord);
	}
	
	/**
	 * 删除数据
	 * @param userProductWarehouseRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserProductWarehouseRecord userProductWarehouseRecord) {
		super.delete(userProductWarehouseRecord);
	}
	
}