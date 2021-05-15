package com.nabobsite.modules.nabob.api.entity;

import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.task.entity.UserTask;
import com.nabobsite.modules.nabob.cms.user.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;

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
            String lang = userInfo.getLang();
            if(StringUtils.isEmpty(lang)){
                userInfo.setLang(I18nUtils.LANG_EN);
            }else{
                if(I18nUtils.LANG_EN.equalsIgnoreCase(lang)){
                    userInfo.setLang(I18nUtils.LANG_EN);
                }else if(I18nUtils.LANG_IN.equalsIgnoreCase(lang)){
                    userInfo.setLang(I18nUtils.LANG_EN);
                }else if(I18nUtils.LANG_ZH.equalsIgnoreCase(lang)){
                    userInfo.setLang(I18nUtils.LANG_EN);
                }else{
                    userInfo.setLang(I18nUtils.LANG_EN);
                }
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
            userAccount.setRewardMoney(LogicStaticContact.USER_TACK_BASE_MONEY);
            userAccount.setAccountStatus(CommonContact.USER_ACCOUNT_STATUS_OK);
            return userAccount;
        }
    }

    /**
     * @desc 初始化用户账户记录信息
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
            return userAccountDetail;
        }
    }

    /**
     * @desc 初始化用户账户日志
     * @author nada
     * @create 2021/5/12 2:58 下午
     */
    public static UserAccountLog initUserAccountLog(String detailId,String title,UserAccount olduUerAccount){
        UserAccountLog userAccountLog = (UserAccountLog)olduUerAccount.clone();
        userAccountLog.setIsNewRecord(true);
        userAccountLog.setTitle(title);
        userAccountLog.setDetailId(detailId);
        return userAccountLog;
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
        order.setType(CommonContact.ORDER_TYPE_RECHANGE);
        order.setOrderStatus(CommonContact.ORDER_STATUS_1);
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
        userTask.setTaskStatus(CommonContact.USER_TASK_STATUS_2);
        return userTask;
    }

}
