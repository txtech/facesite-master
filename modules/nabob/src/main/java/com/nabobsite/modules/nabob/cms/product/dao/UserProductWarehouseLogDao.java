/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseLog;

/**
 * 用户产品仓库日志DAO接口
 * @author face
 * @version 2021-05-17
 */
@MyBatisDao
public interface UserProductWarehouseLogDao extends CrudDao<UserProductWarehouseLog> {
	
}