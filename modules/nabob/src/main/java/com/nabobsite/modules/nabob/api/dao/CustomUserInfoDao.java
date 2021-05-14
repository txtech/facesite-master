package com.nabobsite.modules.nabob.api.dao;

import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

public interface CustomUserInfoDao  {

    /**
     * @desc 修改团队人数
     * @author nada
     * @create 2021/5/14 2:49 下午
     */
    long updateTeamNum(UserInfo userInfo);

    /**
     * @desc 获取达到等级的直推团队人数
     * @author nada
     * @create 2021/5/14 2:44 下午
     */
    Integer getOkLevelTeam1Num(UserInfo userInfo);
}
