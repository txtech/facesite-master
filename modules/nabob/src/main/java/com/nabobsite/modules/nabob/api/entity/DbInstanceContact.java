package com.nabobsite.modules.nabob.api.entity;

import com.jeesite.common.shiro.realms.Da;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccountRecord;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import com.nabobsite.modules.nabob.cms.user.entity.UserRewardRecord;
import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class DbInstanceContact {

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
            userInfo.setTaskEndTime(CommonStaticContact.addDateHour(48));
            userInfo.setLock(CommonStaticContact.USER_LOCK_1);
            userInfo.setUserStatus(CommonStaticContact.USER_STATUS_1);
            if(StringUtils.isEmpty(params.getParentSysId())){
                userInfo.setParentSysId("0");
            }
            if(StringUtils.isEmpty(params.getParentUserId())){
                userInfo.setParentUserId("0");
            }
            if(StringUtils.isEmpty(params.getParent2UserId())){
                userInfo.setParent2UserId("0");
            }
            if(StringUtils.isEmpty(params.getParent3UserId())){
                userInfo.setParent3UserId("0");
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
            userAccount.setTaskMoney(LogicStaticContact.USER_TACK_BASE_MONEY);
            userAccount.setAccountStatus(CommonStaticContact.USER_ACCOUNT_STATUS_OK);
            return userAccount;
        }
    }

    /**
     * @desc 初始化用户账户记录信息
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountRecord initUserAccountRecord(String userId,String accountId,int type,BigDecimal payMoney,BigDecimal totalMoney,String uniqueId,String title,String remarks){
        synchronized (userId) {
            UserAccountRecord userAccountRecord = new UserAccountRecord();
            userAccountRecord.setIsNewRecord(true);
            userAccountRecord.setType(type);
            userAccountRecord.setUserId(userId);
            userAccountRecord.setRemark(title);
            userAccountRecord.setRemarks(remarks);
            userAccountRecord.setAccountId(accountId);
            userAccountRecord.setChangeMoney(payMoney);
            userAccountRecord.setUnique(uniqueId);
            userAccountRecord.setTotalMoney(CommonStaticContact.add(totalMoney,payMoney));
            return userAccountRecord;
        }
    }

    /**
     * @desc 初始化用户账户记录信息
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserRewardRecord initUserRewardRecord(String userId, int type, BigDecimal rewardMoney, BigDecimal totalMoney, String title, String remarks){
        synchronized (userId) {
            UserRewardRecord userRewardRecord = new UserRewardRecord();
            userRewardRecord.setIsNewRecord(true);
            userRewardRecord.setType(type);
            userRewardRecord.setUserId(userId);
            userRewardRecord.setTitle(title);
            userRewardRecord.setRemarks(remarks);
            userRewardRecord.setMoney(rewardMoney);
            userRewardRecord.setTotalMoney(rewardMoney);
            return userRewardRecord;
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

    /**
     * @desc 初始化用户任务信息
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static UserTask initUserTask(String userId,String taskId,int finishNumber){
        UserTask userTask = new UserTask();
        userTask.setIsNewRecord(true);
        userTask.setUserId(userId);
        userTask.setTaskId(taskId);
        userTask.setFinishNumber(finishNumber);
        userTask.setTaskStatus(CommonStaticContact.USER_TASK_STATUS_2);
        return userTask;
    }

}
