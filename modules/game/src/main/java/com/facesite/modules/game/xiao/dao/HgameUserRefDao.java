/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.facesite.modules.game.xiao.entity.HgameUserRef;

/**
 * 用户游戏记录DAO接口
 * @author nada
 * @version 2021-01-12
 */
@MyBatisDao
public interface HgameUserRefDao extends CrudDao<HgameUserRef> {

	/**
	 * @desc 根据ID更新
	 * @author nada
	 * @create 2021/1/19 9:53 下午
	*/
	public Long updateGameUserRef(HgameUserRef hgameUserRef);

	/**
	 * @desc 根据userID和gameID更新
	 * @author nada
	 * @create 2021/1/19 9:54 下午
	*/
	public Long updateResetGameUserRef(HgameUserRef hgameUserRef);
}
