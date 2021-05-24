/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountBackup;

/**
 * 用户账户备份DAO接口
 * @author face
 * @version 2021-05-24
 */
@MyBatisDao
public interface UserAccountBackupDao extends CrudDao<UserAccountBackup> {
	
}