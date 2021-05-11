/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserInfoApiService extends CrudService<UserInfoDao, UserInfo> {

	/**
	 * 获取单条数据
	 * @param userInfo
	 * @return
	 */
	@Override
	public UserInfo get(UserInfo userInfo) {
		return super.get(userInfo);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param userInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserInfo userInfo) {
		super.save(userInfo);
	}

	/**
	 * 更新状态
	 * @param userInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserInfo userInfo) {
		super.updateStatus(userInfo);
	}

	/**
	 * 删除数据
	 * @param userInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserInfo userInfo) {
		super.delete(userInfo);
	}

}
