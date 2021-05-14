package com.nabobsite.modules.nabob.api.dao;

import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

public interface CustomUserInfoDao {

    long updateTeam1(UserInfo userInfo);

    long updateTeam2(UserInfo userInfo);

    long updateTeam3(UserInfo userInfo);

    //获取达到等级的直推团队人数
    Integer getOkLevelTeamNum1(UserInfo userInfo);
}
