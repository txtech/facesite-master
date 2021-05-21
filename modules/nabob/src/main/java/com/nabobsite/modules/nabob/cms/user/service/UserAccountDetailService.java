/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountDetail;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDetailDao;

/**
 * 用户账户Service
 * @author face
 * @version 2021-05-21
 */
@Service
@Transactional(readOnly=true)
public class UserAccountDetailService extends CrudService<UserAccountDetailDao, UserAccountDetail> {
	
	/**
	 * 获取单条数据
	 * @param userAccountDetail
	 * @return
	 */
	@Override
	public UserAccountDetail get(UserAccountDetail userAccountDetail) {
		return super.get(userAccountDetail);
	}
	
	/**
	 * 查询分页数据
	 * @param userAccountDetail 查询条件
	 * @param userAccountDetail.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAccountDetail> findPage(UserAccountDetail userAccountDetail) {
		return super.findPage(userAccountDetail);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAccountDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccountDetail userAccountDetail) {
		super.save(userAccountDetail);
	}
	
	/**
	 * 更新状态
	 * @param userAccountDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccountDetail userAccountDetail) {
		super.updateStatus(userAccountDetail);
	}
	
	/**
	 * 删除数据
	 * @param userAccountDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccountDetail userAccountDetail) {
		super.delete(userAccountDetail);
	}
	
}