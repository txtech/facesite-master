/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.facesite.modules.game.xiao.entity.HgameInfo;

/**
 * 游戏信息DAO接口
 * @author nada
 * @version 2021-01-12
 */
@MyBatisDao
public interface HgameInfoDao extends CrudDao<HgameInfo> {
	
}