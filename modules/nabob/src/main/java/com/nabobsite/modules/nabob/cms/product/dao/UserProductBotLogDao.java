/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBotLog;

/**
 * 用户产品机器人记录DAO接口
 * @author face
 * @version 2021-05-17
 */
@MyBatisDao
public interface UserProductBotLogDao extends CrudDao<UserProductBotLog> {
	
}