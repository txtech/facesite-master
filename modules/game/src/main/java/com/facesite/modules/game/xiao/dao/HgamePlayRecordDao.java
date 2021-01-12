/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.facesite.modules.game.xiao.entity.HgamePlayRecord;

/**
 * 玩游戏记录DAO接口
 * @author nada
 * @version 2021-01-12
 */
@MyBatisDao
public interface HgamePlayRecordDao extends CrudDao<HgamePlayRecord> {
	
}