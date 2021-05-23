/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysNotice;

/**
 * 系统通知DAO接口
 * @author face
 * @version 2021-05-23
 */
@MyBatisDao
public interface SysNoticeDao extends CrudDao<SysNotice> {
	
}