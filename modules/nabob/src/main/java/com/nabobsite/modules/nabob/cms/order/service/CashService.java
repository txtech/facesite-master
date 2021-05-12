/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import com.nabobsite.modules.nabob.cms.order.dao.CashDao;

/**
 * 出款Service
 * @author face
 * @version 2021-05-12
 */
@Service
@Transactional(readOnly=true)
public class CashService extends CrudService<CashDao, Cash> {
	
	/**
	 * 获取单条数据
	 * @param cash
	 * @return
	 */
	@Override
	public Cash get(Cash cash) {
		return super.get(cash);
	}
	
	/**
	 * 查询分页数据
	 * @param cash 查询条件
	 * @param cash.page 分页对象
	 * @return
	 */
	@Override
	public Page<Cash> findPage(Cash cash) {
		return super.findPage(cash);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param cash
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Cash cash) {
		super.save(cash);
	}
	
	/**
	 * 更新状态
	 * @param cash
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Cash cash) {
		super.updateStatus(cash);
	}
	
	/**
	 * 删除数据
	 * @param cash
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Cash cash) {
		super.delete(cash);
	}
	
}