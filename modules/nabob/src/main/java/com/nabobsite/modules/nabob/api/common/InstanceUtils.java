package com.nabobsite.modules.nabob.api.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.cms.order.entity.OrderCash;
import com.nabobsite.modules.nabob.cms.order.entity.OrderPay;
import com.nabobsite.modules.nabob.cms.product.entity.*;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import com.nabobsite.modules.nabob.cms.task.entity.TaskUserReward;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUser;
import com.nabobsite.modules.nabob.cms.team.entity.TeamUserReward;
import com.nabobsite.modules.nabob.cms.user.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:23 上午
 * @Version 1.0
 */
public class InstanceUtils {

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
            userInfo.setIsValid(ContactUtils.USER_VALID_2);
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
            userInfo.setLock(ContactUtils.USER_LOCK_1);
            userInfo.setUserStatus(ContactUtils.USER_STATUS_1);
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
            userAccount.setIncrementMoney(new BigDecimal("0"));
            userAccount.setCommissionMoney(new BigDecimal("0"));
            userAccount.setClaimableMoney(new BigDecimal("0"));
            userAccount.setRechargeMoney(new BigDecimal("0"));
            userAccount.setAiAssetsMoney(new BigDecimal("0"));
            userAccount.setAccountStatus(ContactUtils.USER_ACCOUNT_STATUS_1);
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
            userAccountDetail.setAiAssetsMoney(new BigDecimal("0"));
            userAccountDetail.setTotalMoney(new BigDecimal("0"));
            userAccountDetail.setAvailableMoney(new BigDecimal("0"));
            userAccountDetail.setIncrementMoney(new BigDecimal("0"));
            userAccountDetail.setCommissionMoney(new BigDecimal("0"));
            userAccountDetail.setClaimableMoney(new BigDecimal("0"));
            userAccountDetail.setRechargeMoney(new BigDecimal("0"));
            userAccountDetail.setLedgerType(1);
            return userAccountDetail;
        }
    }

    /**
     * @desc 初始化用户账户日志
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountBackup initUserAccountBackup(String detailId,String title,UserAccount olduUerAccount){
        UserAccountBackup userAccountLog = new UserAccountBackup();
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
    public static OrderPay initOrderInfo(OrderPay params, String orderNo, SysChannel sysChannel ){
        OrderPay order = (OrderPay)params.clone();
        order.setIsNewRecord(true);
        order.setOrderNo(orderNo);
        order.setPayRate(sysChannel.getPayRate());
        order.setPayType(ContactUtils.ORDER_PAY_TYPE_1);
        order.setChannelId(sysChannel.getId());
        order.setActualMoney(params.getPayMoney());
        order.setType(ContactUtils.ORDER_TYPE_RECHANGE);
        order.setOrderStatus(ContactUtils.ORDER_STATUS_1);
        return order;
    }

    /**
     * @desc 初始化订单信息
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static OrderCash initOrderCash(OrderCash params, String orderNo, SysChannel sysChannel ){
        OrderCash order = (OrderCash)params.clone();
        order.setIsNewRecord(true);
        order.setOrderNo(orderNo);
        order.setCashRate(sysChannel.getPayRate());
        order.setChannelId(sysChannel.getId());
        order.setCashMoney(params.getCashMoney());
        order.setType(ContactUtils.ORDER_TYPE_RECHANGE);
        order.setCashStatus(ContactUtils.ORDER_STATUS_1);
        order.setServiceCharge(params.getCashMoney().multiply(sysChannel.getPayRate()));
        return order;
    }

    /**
     * @desc 初始化用户奖励
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static TaskUserReward initUserTaskReward(String userId, String taskId, int type, String title, int finishNumber, int taskNum, BigDecimal rewardMoney){
        TaskUserReward userTaskReward = new TaskUserReward();
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
    public static ProductUserBot initProductUserBot(ProductUserBotLog userProductBotLog){
        ProductUserBot userProductBot = new ProductUserBot();
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
    public static ProductUserWarehouse initProductUserWarehouse(String userId, String warehouseId, BigDecimal asstesHeldMoney){
        ProductUserWarehouse userProductWarehouse = new ProductUserWarehouse();
        userProductWarehouse.setIsNewRecord(true);
        userProductWarehouse.setUserId(userId);
        userProductWarehouse.setWarehouseId(warehouseId);
        userProductWarehouse.setAsstesHeldMoney(asstesHeldMoney);
        userProductWarehouse.setTeamIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setPersonalIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setAccumulativeIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setTeamAccumulativeIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setPersonalAccumulativeIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setAiAssetsMoney(new BigDecimal("0"));
        userProductWarehouse.setIncomeMoney(new BigDecimal("0"));
        userProductWarehouse.setTeamUpdateTime(new Date());
        userProductWarehouse.setPsersonUpdateTime(new Date());
        return userProductWarehouse;
    }

    /**
     * @desc 初始化云仓库操作记录初始化
     * @author nada
     * @create 2021/5/12 2:59 下午
     */
    public static ProductUserWarehouseLog initProductUserWarehouseLog(String userId, String warehouseId, int logType,int productType, String title, BigDecimal money){
        ProductUserWarehouseLog productUserWarehouseLog = new ProductUserWarehouseLog();
        productUserWarehouseLog.setIsNewRecord(true);
        productUserWarehouseLog.setUserId(userId);
        productUserWarehouseLog.setWarehouseId(warehouseId);
        productUserWarehouseLog.setType(logType);
        productUserWarehouseLog.setProductType(productType);
        productUserWarehouseLog.setTitle(title);
        productUserWarehouseLog.setIncomeMoney(money);
        return productUserWarehouseLog;
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
            userAccountWarehouse.setTeamUpdateTime(new Date());
            userAccountWarehouse.setPsersonUpdateTime(new Date());
            userAccountWarehouse.setAsstesHeldMoney(new BigDecimal("0"));
            userAccountWarehouse.setAccumulativeIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setPersonalIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setPersonalAccumulativeIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setTeamIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setTeamAccumulativeIncomeMoney(new BigDecimal("0"));
            userAccountWarehouse.setIncomeMoney(new BigDecimal("0"));
            return userAccountWarehouse;
        }
    }

    /**
     * @desc 初始化用户奖励账户
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountTask initUserAccountTask(String userId){
        synchronized (userId){
            UserAccountTask userTask = new UserAccountTask();
            userTask.setIsNewRecord(true);
            userTask.setUserId(userId);
            userTask.setTaskStatus(ContactUtils.USER_TASK_STATUS_1);
            userTask.setTaskInitialNum(ContactUtils.USER_TACK_BASE_MONEY);
            userTask.setTaskOrderNum(0);
            userTask.setTaskStartDay(new Date());
            userTask.setTotalRewardMoney(new BigDecimal("1000"));
            userTask.setTaskEndDay(ContactUtils.addDateHour(24));
            userTask.setTaskFinishData(new JSONObject().toJSONString());
            return userTask;
        }
    }

    /**
     * @desc 初始化用户团队
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static TeamUser initUserTeam(String userId){
        synchronized (userId){
            TeamUser userTeam = new TeamUser();
            userTeam.setIsNewRecord(true);
            userTeam.setUserId(userId);
            userTeam.setValidNum(0);
            userTeam.setTeamNum(0);
            userTeam.setTeam1Num(0);
            userTeam.setTeam2Num(0);
            userTeam.setTeam3Num(0);
            userTeam.setTeamDepositMoney(new BigDecimal("0"));
            userTeam.setTeamMoney(new BigDecimal("0"));
            userTeam.setTeam1Money(new BigDecimal("0"));
            userTeam.setTeam2Money(new BigDecimal("0"));
            userTeam.setTeam3Money(new BigDecimal("0"));
            return userTeam;
        }
    }

    /**
     * 初始化团队用户奖励
     * @param userId
     * @return
     */
    public static TeamUserReward initTeamUserReward(String userId,String rewardId,String title,Integer finishNum,BigDecimal rewardMoney){
        synchronized (userId){
            TeamUserReward  teamUserReward = new TeamUserReward();
            teamUserReward.setIsNewRecord(true);
            teamUserReward.setUserId(userId);
            teamUserReward.setRewardId(rewardId);
            teamUserReward.setTitle(title);
            teamUserReward.setFinishNum(finishNum);
            teamUserReward.setRewardMoney(rewardMoney);
            return teamUserReward;
        }
    }


    /**
     * 初始化分润
     * @return
     */
    public static UserProfitDetail initUserProfitDetail(String userId,String title,int type,String parent1UserId,String parent2UserId,String parent3UserId,BigDecimal profitRate,BigDecimal prifitMoney,BigDecimal updateMoney){
        synchronized (userId){
            UserProfitDetail userProfitDetail = new UserProfitDetail();
            userProfitDetail.setUserId(userId);
            userProfitDetail.setType(type);
            userProfitDetail.setTitle(title);
            userProfitDetail.setMoney(updateMoney);
            userProfitDetail.setProfitRate(profitRate);
            userProfitDetail.setProfitMoney(prifitMoney);
            userProfitDetail.setParent1UserId(parent1UserId);
            userProfitDetail.setParent2UserId(parent2UserId);
            userProfitDetail.setParent3UserId(parent3UserId);
            return userProfitDetail;
        }
    }

    /**
     * @param userId
     * @return
     */
    public static ProductUserBotAistart initProductUserBotAistart(String userId,String botId,int level,ProductBot productBot){
        synchronized (userId){
            ProductUserBotAistart productUserBotAistart = new ProductUserBotAistart();
            productUserBotAistart.setIsNewRecord(true);
            productUserBotAistart.setUserId(userId);
            productUserBotAistart.setBotId(botId);
            productUserBotAistart.setLevel(level);
            productUserBotAistart.setAiDay(7);
            productUserBotAistart.setDailyNum(0);
            productUserBotAistart.setStartDate(new Date());
            productUserBotAistart.setEndDate(ContactUtils.addDateHour(24*7));
            productUserBotAistart.setAiRate(productBot.getCommissionRate());
            productUserBotAistart.setAiRateOther(productBot.getCommissionRateOther());
            productUserBotAistart.setAiMoney(productBot.getPrice());
            productUserBotAistart.setAiStatus(2);
            return productUserBotAistart;
        }
    }
}
