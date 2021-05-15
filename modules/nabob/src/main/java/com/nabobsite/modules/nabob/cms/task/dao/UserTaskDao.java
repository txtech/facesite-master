/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;

/**
 * 用户任务DAO接口
 * @author face
 * @version 2021-05-13
 */
@MyBatisDao
public interface UserTaskDao extends CrudDao<UserTask> {
	
}