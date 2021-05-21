package com.nabobsite.modules.nabob.api.entity;

import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBot;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductBotLog;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouse;
import com.nabobsite.modules.nabob.cms.product.entity.UserProductWarehouseLog;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.task.entity.UserTaskReward;
import com.nabobsite.modules.nabob.cms.user.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class InstanceContact {

    /**
     * @desc 初始化用户信息
     * @author nada
     * @create 2021/5/12 2:58 下午
    */
    public static UserInfo initUserInfo(UserInfo userInfo){
        String accountNo = userInfo.getAccountNo();
        synchronized (accountNo) {
            userInfo.setIsNewRecord(true);
            userInfo.setName(accountNo);
            userInfo.setPhoneNumber(accountNo);
            userInfo.setLevel(0);
            userInfo.setTeamNum(0);
            userInfo.setTeam1Num(0);
            userInfo.setTeam2Num(0);
            userInfo.setTeam3Num(0);
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
            userInfo.setLock(CommonContact.USER_LOCK_1);
            userInfo.setUserStatus(CommonContact.USER_STATUS_1);
            if(StringUtils.isEmpty(userInfo.getParentSysId())){
                userInfo.setParentSysId("0");
            }
            if(StringUtils.isEmpty(userInfo.getParent1UserId())){
                userInfo.setParent1UserId("0");
            }
            if(StringUtils.isEmpty(userInfo.getParent2UserId())){
                userInfo.setParent2UserId("0");
            }
            if(StringUtils.isEmpty(userInfo.getParent3UserId())){
                userInfo.setParent3UserId("0");
            }
            return userInfo;
        }
    }

    /**
     * @desc 初始化用户账户
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
            userAccount.setIncrementMoney(new BigDecimal("0"));
            userAccount.setCommissionMoney(new BigDecimal("0"));
            userAccount.setClaimableMoney(new BigDecimal("0"));
            userAccount.setTeamMoney(new BigDecimal("0"));
            userAccount.setTeam1Money(new BigDecimal("0"));
            userAccount.setTeam2Money(new BigDecimal("0"));
            userAccount.setTeam3Money(new BigDecimal("0"));
            userAccount.setRewardMoney(LogicStaticContact.USER_TACK_BASE_MONEY);
            userAccount.setAccountStatus(CommonContact.USER_ACCOUNT_STATUS_1);
            return userAccount;
        }
    }

    /**
     * @desc 初始化用户账户明细
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountDetail initUserAccountDetail(String userId,int type,String uniqueId,String title){
        synchronized (userId) {
            UserAccountDetail userAccountDetail = new UserAccountDetail();
            userAccountDetail.setIsNewRecord(true);
            userAccountDetail.setUserId(userId);
            userAccountDetail.setTitle(title);
            userAccountDetail.setType(type);
            userAccountDetail.setUnique(uniqueId);
            userAccountDetail.setTotalMoney(new BigDecimal("0"));
            userAccountDetail.setAvailableMoney(new BigDecimal("0"));
            userAccountDetail.setWarehouseMoney(new BigDecimal("0"));
            userAccountDetail.setAiAssetsMoney(new BigDecimal("0"));
            userAccountDetail.setIncomeMoney(new BigDecimal("0"));
            userAccountDetail.setIncrementMoney(new BigDecimal("0"));
            userAccountDetail.setCommissionMoney(new BigDecimal("0"));
            userAccountDetail.setClaimableMoney(new BigDecimal("0"));
            userAccountDetail.setRewardMoney(new BigDecimal("0"));
            userAccountDetail.setLedgerType(1);
            return userAccountDetail;
        }
    }

    /**
     * @desc 初始化用户账户日志
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountLog initUserAccountLog(String detailId,String title,UserAccount olduUerAccount){
        UserAccountLog userAccountLog = new UserAccountLog();
        BeanUtils.copyProperties(olduUerAccount, userAccountLog);
        userAccountLog.setIsNewRecord(true);
        userAccountLog.setId(null);
        userAccountLog.setTitle(title);
        userAccountLog.setDetailId(detailId);
        return userAccountLog;
    }

    /**
     * @desc 初始化订单信息
     * @author nada
     * @create 2021/5/12 2:59 下午
    */
    public static Order initOrderInfo(Order params, String orderNo, SysChannel sysChannel ){
        Order order = (Order)params.clone();
        order.setIsNewRecord(true);
        order.setOrderNo(orderNo);
        order.setPayRate(sysChannel.getPayRate());
        order.setPayType(CommonContact.ORDER_PAY_TYPE_1);
        order.setChannelId(sysChannel.getId());
        order.setActualMoney(params.getPayMoney());
        order.setType(CommonContact.ORDER_TYPE_RECHANGE);
        order.setOrderStatus(CommonContact.ORDER_STATUS_1);
        return order;
    }

    /**
     * @desc 初始化用户奖励
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static UserTaskReward initUserTaskReward(String userId,String taskId,int type,String title,int finishNumber,int taskNum,BigDecimal rewardMoney){
        UserTaskReward userTaskReward = new UserTaskReward();
        userTaskReward.setIsNewRecord(true);
        userTaskReward.setUserId(userId);
        userTaskReward.setTaskId(taskId);
        userTaskReward.setTitle(title);
        userTaskReward.setType(type);
        userTaskReward.setTaskNumber(taskNum);
        userTaskReward.setFinishNum(finishNumber);
        userTaskReward.setRewardMoney(rewardMoney);
        return userTaskReward;
    }

    /**
     * @desc 初始化用户无人机产品
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static UserProductBot initUserProductBot(UserProductBotLog userProductBotLog){
        UserProductBot userProductBot = new UserProductBot();
        userProductBot.setIsNewRecord(true);
        userProductBot.setUserId(userProductBotLog.getUserId());
        userProductBot.setBotId(userProductBotLog.getBotId());
        userProductBot.setTodayOrders(1);
        userProductBot.setTodayIncomeMoney(userProductBotLog.getIncomeMoney());
        userProductBot.setTodayTeamIncome(new BigDecimal("0"));
        userProductBot.setYesterdayIncomeMoney(new BigDecimal("0"));
        userProductBot.setYesterdayTeamIncomeMoney(new BigDecimal("0"));
        return userProductBot;
    }

    /**
     * @desc 初始化云仓库定投初始化
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static UserProductWarehouse initUserProductWarehouse(String userId,String warehouseId,BigDecimal asstesHeldMoney){
        UserProductWarehouse userProductWarehouse = new UserProductWarehouse();
        userProductWarehouse.setIsNewRecord(true);
        userProductWarehouse.setUserId(userId);
        userProductWarehouse.setWarehouseId(warehouseId);
        userProductWarehouse.setAsstesHeldMoney(asstesHeldMoney);
        userProductWarehouse.setTeamIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setPersonalIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setAccumulativeIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setTeamAccumulativeIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setPersonalAccumulativeIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setTeamUpdateTime(new Date());
        userProductWarehouse.setPsersonUpdateTime(new Date());
        return userProductWarehouse;
    }

    /**
     * @desc 初始化云仓库操作记录初始化
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static UserProductWarehouseLog initUserProductWarehouseLog(String userId,String warehouseId,int type,String title,BigDecimal money){
        UserProductWarehouseLog userProductWarehouseLog = new UserProductWarehouseLog();
        userProductWarehouseLog.setIsNewRecord(true);
        userProductWarehouseLog.setUserId(userId);
        userProductWarehouseLog.setWarehouseId(warehouseId);
        userProductWarehouseLog.setType(type);
        userProductWarehouseLog.setTitle(title);
        userProductWarehouseLog.setIncomeMoney(money);
        return userProductWarehouseLog;
    }

    /**
     * @desc 初始化云仓库账户
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountWarehouse initUserAccountWarehouse(String userId){
        synchronized (userId) {
            UserAccountWarehouse userAccountWarehouse = new UserAccountWarehouse();
            userAccountWarehouse.setIsNewRecord(true);
            userAccountWarehouse.setUserId(userId);
            userAccountWarehouse.setAsstesHeldMoney(new BigDecimal("0"));
            userAccountWarehouse.setAccumulativeIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setPersonalIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setPersonalAccumulativeIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setTeamIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setTeamAccumulativeIncomeMoney(new BigDecimal("0"));
            return userAccountWarehouse;
        }
    }

    /**
     * @desc 初始化用户奖励账户
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserTask initUserTask(String userId){
        synchronized (userId){
            UserTask userTask = new UserTask();
            userTask.setIsNewRecord(true);
            userTask.setUserId(userId);
            userTask.setTaskStatus(CommonContact.USER_TASK_STATUS_1);
            userTask.setTaskInitialNum(LogicStaticContact.USER_TACK_BASE_MONEY);
            userTask.setTaskOrderNum(0);
            userTask.setTaskStartDay(new Date());
            userTask.setTaskEndDay(CommonContact.addDateHour(24));
            userTask.setTaskFinishData(new JSONObject().toJSONString());
            return userTask;
        }
    }
}
