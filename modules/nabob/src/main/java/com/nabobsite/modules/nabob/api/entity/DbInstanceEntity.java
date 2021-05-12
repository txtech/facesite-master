package com.nabobsite.modules.nabob.api.entity;

import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class DbInstanceEntity {

    public static UserInfo initUserInfo(UserInfo params){
        String accountNo = params.getAccountNo();
        UserInfo userInfo = (UserInfo)params.clone();
        userInfo.setIsNewRecord(true);
        userInfo.setName(accountNo);
        userInfo.setPhoneNumber(accountNo);
        userInfo.setLevel(0);
        userInfo.setTeamNum(0);
        userInfo.setTeamNum1(0);
        userInfo.setTeamNum2(0);
        userInfo.setTeamNum3(0);
        userInfo.setUserStatus(UserStaticContact.USER_STATUS_OK);
        if(StringUtils.isEmpty(params.getParentUserId())){
            userInfo.setParentUserId("0");
        }
        if(StringUtils.isEmpty(params.getParentUserId())){
            userInfo.setParentUserId("0");
        }
        return userInfo;
    }
}
