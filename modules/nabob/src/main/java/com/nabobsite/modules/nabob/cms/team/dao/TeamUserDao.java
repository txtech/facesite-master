/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.team.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;

/**
 * 用户团队DAO接口
 * @author face
 * @version 2021-05-24
 */
@MyBatisDao
public interface TeamUserDao extends CrudDao<TeamUser> {
	
}