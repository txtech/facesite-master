/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;

/**
 * 用户账户DAO接口
 * @author face
 * @version 2021-05-17
 */
@MyBatisDao
public interface UserAccountDao extends CrudDao<UserAccount> {

    long updateAccountMoney(UserAccount userAccount);
}
