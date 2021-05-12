/**
 * Copyright (c) 2013-Now  All rights reserved.
 */
package com.nabobsite.modules.nabob.api.service;

import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.api.entity.RedisPrefixContant;
import com.nabobsite.modules.nabob.cms.product.dao.ProductBotDao;
import com.nabobsite.modules.nabob.cms.product.dao.ProductWarehouseDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouse;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.utils.CommonResult;
import com.nabobsite.modules.nabob.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户账户Service
 * @author face
 * @version 2021-05-10
 */
@Service
@Transactional(readOnly=true)
public class UserAccountApiService extends CrudService<UserAccountDao, UserAccount> {

	/**
	 * 获取单条数据
	 * @param userAccount
	 * @return
	 */
	@Override
	public UserAccount get(UserAccount userAccount) {
		return super.get(userAccount);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param userAccount
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserAccount userAccount) {
		super.save(userAccount);
	}

	/**
	 * 更新状态
	 * @param userAccount
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAccount userAccount) {
		super.updateStatus(userAccount);
	}

	/**
	 * 删除数据
	 * @param userAccount
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAccount userAccount) {
		super.delete(userAccount);
	}

}
