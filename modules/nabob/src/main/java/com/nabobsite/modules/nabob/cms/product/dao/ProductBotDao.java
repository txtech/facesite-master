/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductBot;

/**
 * 产品机器人DAO接口
 * @author face
 * @version 2021-05-20
 */
@MyBatisDao
public interface ProductBotDao extends CrudDao<ProductBot> {
	
}