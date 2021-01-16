/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.facesite.modules.game.xiao.entity.HgamePlayLog;

/**
 * 消消乐游戏日志DAO接口
 * @author nada
 * @version 2021-01-16
 */
@MyBatisDao
public interface HgamePlayLogDao extends CrudDao<HgamePlayLog> {
	
}