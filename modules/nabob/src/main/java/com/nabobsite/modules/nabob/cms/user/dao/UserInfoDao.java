/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

/**
 * 会员用户DAO接口
 * @author face
 * @version 2021-05-23
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {

    /**
     * @desc 获取达到等级的直推团队人数
     * @author nada
     * @create 2021/5/14 2:44 下午
     */
    Integer getOkLevelTeam1Num(UserInfo userInfo);
}