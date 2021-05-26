/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.team.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

/**
 * 用户团队DAO接口
 * @author face
 * @version 2021-05-26
 */
@MyBatisDao
public interface TeamUserDao extends CrudDao<TeamUser> {

    /**
     * @desc 修改团队人数
     * @author nada
     * @create 2021/5/14 2:49 下午
     */
    long updateTeamNum(TeamUser teamUser);
}