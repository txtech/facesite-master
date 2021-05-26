package com.nabobsite.modules.nabob.api.dao;

import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

public interface CustomUserInfoDao  {

    /**
     * @desc 修改团队人数
     * @author nada
     * @create 2021/5/14 2:49 下午
     */
    long updateTeamNum(TeamUser teamUser);

    /**
     * @desc 获取达到等级的直推团队人数
     * @author nada
     * @create 2021/5/14 2:44 下午
     */
    Integer getOkLevelTeam1Num(UserInfo userInfo);

    //    private String smsCode; //短信验证码
//    private String codeKey; //图片验证码key
//    private String imgCode; //图片验证码code
//      private String oldPassword;
}
