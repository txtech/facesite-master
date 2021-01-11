/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.facesite.modules.product.entity.TbProductCat;

/**
 * 商品分类DAO接口
 * @author nada
 * @version 2021-01-07
 */
@MyBatisDao
public interface TbProductCatDao extends CrudDao<TbProductCat> {
	
}