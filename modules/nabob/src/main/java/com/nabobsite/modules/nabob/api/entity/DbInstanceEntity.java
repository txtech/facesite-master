package com.nabobsite.modules.nabob.api.entity;

import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountRecord;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

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
     * @desc 初始化用户账户信息
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccount initUserAccount(String userId){
        synchronized (userId) {
            UserAccount userAccount = new UserAccount();
            userAccount.setIsNewRecord(true);
            userAccount.setUserId(userId);
            userAccount.setTotalMoney(new BigDecimal("0"));
            userAccount.setAvailableMoney(new BigDecimal("0"));
            userAccount.setWarehouseMoney(new BigDecimal("0"));
            userAccount.setAiAssetsMoney(new BigDecimal("0"));
            userAccount.setIncomeMoney(new BigDecimal("0"));
            userAccount.setAccountStatus(CommonStaticContact.USER_ACCOUNT_STATUS_OK);
            return userAccount;
        }
    }

    /**
     * @desc 初始化用户账户记录信息
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountRecord initUserAccountRecord(String userId,String accountId,BigDecimal payMoney,BigDecimal totalMoney,String remark){
        synchronized (userId) {
            UserAccountRecord userAccountRecord = new UserAccountRecord();
            userAccountRecord.setIsNewRecord(true);
            userAccountRecord.setType(1);
            userAccountRecord.setRemark(remark);
            userAccountRecord.setUserId(userId);
            userAccountRecord.setAccountId(accountId);
            userAccountRecord.setTotalMoney(CommonStaticContact.add(totalMoney,payMoney));
            userAccountRecord.setChangeMoney(payMoney);
            return userAccountRecord;
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
