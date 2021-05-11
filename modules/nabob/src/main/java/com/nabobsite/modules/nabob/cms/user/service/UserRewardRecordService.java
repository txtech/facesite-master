/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserRewardRecord;
import com.nabobsite.modules.nabob.cms.user.dao.UserRewardRecordDao;

/**
 * 用户收益明细Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserRewardRecordService extends CrudService<UserRewardRecordDao, UserRewardRecord> {

	/**
	 * 获取单条数据
	 * @param userRewardRecord
	 * @return
	 */
	@Override
	public UserRewardRecord get(UserRewardRecord userRewardRecord) {
		return super.get(userRewardRecord);
	}

	/**
	 * 查询分页数据
	 * @param userRewardRecord 查询条件
	 * @param userRewardRecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserRewardRecord> findPage(UserRewardRecord userRewardRecord) {
		return super.findPage(userRewardRecord);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param userRewardRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserRewardRecord userRewardRecord) {
		super.save(userRewardRecord);
	}

	/**
	 * 更新状态
	 * @param userRewardRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserRewardRecord userRewardRecord) {
		super.updateStatus(userRewardRecord);
	}

	/**
	 * 删除数据
	 * @param userRewardRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserRewardRecord userRewardRecord) {
		super.delete(userRewardRecord);
	}

}
