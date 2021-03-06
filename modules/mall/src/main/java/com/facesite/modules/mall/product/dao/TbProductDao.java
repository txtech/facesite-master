/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.mall.product.dao;

import com.facesite.modules.mall.product.entity.TbProduct;
import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;

/**
 * 商品表DAO接口
 * @author nada
 * @version 2021-01-07
 */
@MyBatisDao
public interface TbProductDao extends CrudDao<TbProduct> {

}
