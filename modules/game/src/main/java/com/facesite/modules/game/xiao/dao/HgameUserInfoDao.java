/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.dao;

import com.facesite.modules.game.xiao.entity.HgameUserRef;
import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.facesite.modules.game.xiao.entity.HgameUserInfo;

/**
 * 用户信息DAO接口
 * @author nada
 * @version 2021-01-12
 */
@MyBatisDao
public interface HgameUserInfoDao extends CrudDao<HgameUserInfo> {

    public  int addSequence();

    public  Long getNextSequence();

    public Long updateUserHbeans(HgameUserInfo hgameUserInfo);

    public Long updateRestartUserHbeans(HgameUserInfo hgameUserInfo);

}
