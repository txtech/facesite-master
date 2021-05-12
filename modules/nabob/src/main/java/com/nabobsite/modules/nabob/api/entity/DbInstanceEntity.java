package com.nabobsite.modules.nabob.api.entity;

import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class DbInstanceEntity {

    /**
     * @desc 初始化用户信息
     * @author nada
     * @create 2021/5/12 2:58 下午
    */
    public static UserInfo initUserInfo(UserInfo params){
        String accountNo = params.getAccountNo();
        synchronized (accountNo) {
            UserInfo userInfo = (UserInfo)params.clone();
            userInfo.setIsNewRecord(true);
            userInfo.setName(accountNo);
            userInfo.setPhoneNumber(accountNo);
            userInfo.setLevel(0);
            userInfo.setTeamNum(0);
            userInfo.setTeamNum1(0);
            userInfo.setTeamNum2(0);
            userInfo.setTeamNum3(0);
            userInfo.setUserStatus(CommonStaticContact.USER_STATUS_OK);
            if(StringUtils.isEmpty(params.getParentUserId())){
                userInfo.setParentUserId("0");
            }
            if(StringUtils.isEmpty(params.getParentUserId())){
                userInfo.setParentUserId("0");
            }
            return userInfo;
        }
    }

    /**
     * @desc 初始化订单信息
     * @author nada
     * @create 2021/5/12 2:59 下午
    */
    public static Order initOrderInfo(Order params,String orderNo){
        Order order = (Order)params.clone();
        order.setIsNewRecord(true);
        order.setOrderNo(orderNo);
        order.setActualMoney(params.getPayMoney());
        order.setType(CommonStaticContact.ORDER_TYPE_RECHANGE);
        order.setOrderStatus(CommonStaticContact.ORDER_STATUS_1);
        return order;
    }
}
