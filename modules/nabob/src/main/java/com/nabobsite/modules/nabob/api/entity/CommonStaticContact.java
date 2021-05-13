package com.nabobsite.modules.nabob.api.entity;

import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;

import java.math.BigDecimal;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:25 上午
 * @Version 1.0
 */
public class CommonStaticContact {
    //用户状态
    public final static int USER_STATUS_1 = 1;//正常
    public final static int USER_STATUS_3 = 3;//禁用

    //用户解锁状态
    public final static int USER_LOCK_1 = 1;//正常
    public final static int USER_LOCK_2 = 2;//锁定

    //用户账户状态
    public final static int USER_ACCOUNT_STATUS_OK = 1;//正常
    public final static int USER_ACCOUNT_STATUS_ENABLE = 2;//冻结

    //订单类型
    public final static int ORDER_TYPE_RECHANGE = 1;//充值

    //订单类型
    //1: 待支付 2:支付中 3:支付失败 4:支付成功 9:退款
    public final static int ORDER_STATUS_1 = 1;
    public final static int ORDER_STATUS_2 = 2;
    public final static int ORDER_STATUS_3 = 3;
    public final static int ORDER_STATUS_4 = 4;
    public final static int ORDER_STATUS_9 = 9;

    /**
     * @desc 解析数据库返回接口
     * @author nada
     * @create 2021/5/12 2:39 下午
     */
    public static Boolean dbResult(long dbResult){
        if(dbResult > 0){
            return true;
        }else{
            return false;
        }
    }

    //加法
    public static BigDecimal add(BigDecimal num1, BigDecimal num2){
        return num1.add(num2);
    }
    //减法
    public static BigDecimal subtract(BigDecimal num1,BigDecimal num2){
        return num1.subtract(num2);
    }
    //乘法
    public static BigDecimal multiply(BigDecimal num1,BigDecimal num2){
        return num1.multiply(num2);
    }
    //除法
    public static BigDecimal divide(BigDecimal num1,BigDecimal num2){
        return num1.divide(num2);
    }
    //是否大于：num1 > num2
    public static Boolean isBigger(BigDecimal num1,BigDecimal num2){
        if(num1 == null || num2 == null){
            return false;
        }
        if(num1.compareTo(num2) == 1){
            return true;
        }
        return false;
    }
    //是否小于：num1 < num2
    public static Boolean isLesser(BigDecimal num1,BigDecimal num2){
        if(num1 == null || num2 == null){
            return false;
        }
        if(num1.compareTo(num2) == -1){
            return true;
        }
        return false;
    }
    //是否等于
    public static Boolean isEqual(BigDecimal num1,BigDecimal num2){
        if(num1 == null || num2 == null){
            return false;
        }
        if(num1.compareTo(num2) == 0){
            return true;
        }
        return false;
    }
    //是否大于等于：num1 >= num2
    public static Boolean isBiggerOrEqual(BigDecimal num1,BigDecimal num2){
        if(num1 == null || num2 == null){
            return false;
        }
        if(num1.compareTo(num2) > -1){
            return true;
        }
        return false;
    }
    //是否小于等于：num1 <= num2
    public static Boolean isLesserOrEqual(BigDecimal num1,BigDecimal num2){
        if(num1 == null || num2 == null){
            return false;
        }
        if(num1.compareTo(num2) < 1){
            return true;
        }
        return false;
    }
}
