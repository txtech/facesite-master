/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.UserProfitDetail;
import com.nabobsite.modules.nabob.cms.user.dao.UserProfitDetailDao;

/**
 * 用户分润Service
 * @author face
 * @version 2021-05-26
 */
@Service
@Transactional(readOnly=true)
public class UserProfitDetailService extends CrudService<UserProfitDetailDao, UserProfitDetail> {
	
	/**
	 * 获取单条数据
	 * @param userProfitDetail
	 * @return
	 */
	@Override
	public UserProfitDetail get(UserProfitDetail userProfitDetail) {
		return super.get(userProfitDetail);
	}
	
	/**
	 * 查询分页数据
	 * @param userProfitDetail 查询条件
	 * @param userProfitDetail.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserProfitDetail> findPage(UserProfitDetail userProfitDetail) {
		return super.findPage(userProfitDetail);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userProfitDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserProfitDetail userProfitDetail) {
		super.save(userProfitDetail);
	}
	
	/**
	 * 更新状态
	 * @param userProfitDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserProfitDetail userProfitDetail) {
		super.updateStatus(userProfitDetail);
	}
	
	/**
	 * 删除数据
	 * @param userProfitDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserProfitDetail userProfitDetail) {
		super.delete(userProfitDetail);
	}
	
}