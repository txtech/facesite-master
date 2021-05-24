/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.api.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserWarehouse;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountWarehouse;

/**
 * 用户账户DAO接口
 * @author face
 * @version 2021-05-13
 */
public interface CustomUserAccountDao  {

    long updateAccountMoney(UserAccount userAccount);

    long updateAccountWarehouse(UserAccountWarehouse userAccountWarehouse);

    long updateUserWarehousee(ProductUserWarehouse productUserWarehouse);
}
