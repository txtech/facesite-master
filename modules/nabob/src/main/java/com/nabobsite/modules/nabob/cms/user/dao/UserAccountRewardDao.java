/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountReward;

/**
 * 用户账户明细DAO接口
 * @author face
 * @version 2021-05-15
 */
@MyBatisDao
public interface UserAccountRewardDao extends CrudDao<UserAccountReward> {
	
}