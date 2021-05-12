/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.order.dao.CashDao;
import com.nabobsite.modules.nabob.cms.order.entity.Cash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出款Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class CashApiService extends CrudService<CashDao, Cash> {

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
