/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.user.entity.MemberShip;

/**
 * 任务管理DAO接口
 * @author face
 * @version 2021-05-20
 */
@MyBatisDao
public interface MemberShipDao extends CrudDao<MemberShip> {
	
}