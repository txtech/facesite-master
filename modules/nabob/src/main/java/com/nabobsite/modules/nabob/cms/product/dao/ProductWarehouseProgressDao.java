/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductWarehouseProgress;

/**
 * 用户任务进行中DAO接口
 * @author face
 * @version 2021-05-23
 */
@MyBatisDao
public interface ProductWarehouseProgressDao extends CrudDao<ProductWarehouseProgress> {
	
}