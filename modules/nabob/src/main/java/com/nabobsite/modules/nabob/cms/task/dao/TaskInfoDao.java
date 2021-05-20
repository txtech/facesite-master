/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.task.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.task.entity.TaskInfo;

/**
 * 任务列表DAO接口
 * @author face
 * @version 2021-05-20
 */
@MyBatisDao
public interface TaskInfoDao extends CrudDao<TaskInfo> {
	
}