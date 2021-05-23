/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfoMembership;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoMembershipDao;

/**
 * 会员资格Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class UserInfoMembershipService extends CrudService<UserInfoMembershipDao, UserInfoMembership> {
	
	/**
	 * 获取单条数据
	 * @param userInfoMembership
	 * @return
	 */
	@Override
	public UserInfoMembership get(UserInfoMembership userInfoMembership) {
		return super.get(userInfoMembership);
	}
	
	/**
	 * 查询分页数据
	 * @param userInfoMembership 查询条件
	 * @param userInfoMembership.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserInfoMembership> findPage(UserInfoMembership userInfoMembership) {
		return super.findPage(userInfoMembership);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userInfoMembership
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserInfoMembership userInfoMembership) {
		super.save(userInfoMembership);
	}
	
	/**
	 * 更新状态
	 * @param userInfoMembership
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserInfoMembership userInfoMembership) {
		super.updateStatus(userInfoMembership);
	}
	
	/**
	 * 删除数据
	 * @param userInfoMembership
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserInfoMembership userInfoMembership) {
		super.delete(userInfoMembership);
	}
	
}