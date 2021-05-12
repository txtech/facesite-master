/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountRecord;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountRecordDao;

/**
 * 账户账务明显Service
 * @author face
 * @version 2021-05-12
 */
@Service
@Transactional(readOnly=true)
public class UserAccountRecordService extends CrudService<UserAccountRecordDao, UserAccountRecord> {
	
	/**
	 * 获取单条数据
	 * @param userAccountRecord
	 * @return
	 */
	@Override
	public UserAccountRecord get(UserAccountRecord userAccountRecord) {
		return super.get(userAccountRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountRecord 查询条件
	 * @param userAccountRecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountRecord> findPage(UserAccountRecord userAccountRecord) {
		return super.findPage(userAccountRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountRecord userAccountRecord) {
		super.save(userAccountRecord);
	}
	
	/**
	 * 更新状态
	 * @param userAccountRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountRecord userAccountRecord) {
		super.updateStatus(userAccountRecord);
	}
	
	/**
	 * 删除数据
	 * @param userAccountRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountRecord userAccountRecord) {
		super.delete(userAccountRecord);
	}
	
}