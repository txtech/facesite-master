package com.nabobsite.modules.nabob.api.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:16 下午
 * @Version 1.0
 */
public class LogicStaticContact {

    //用户升级人数
    public final static int USER_LEVEL_UP_TEAM_NUM = 5;
    //用户注册奖励
    public final static BigDecimal USER_REGISTER_REWARD = new BigDecimal("100");
    //用户注册奖励
    public final static BigDecimal USER_TACK_BASE_MONEY = new BigDecimal("990");
    //用户注册奖励
    public final static BigDecimal PRODUCT_COMMISSION_OTHER_RATE = new BigDecimal("0.5");


    //用户等级
    public final static int USER_LEVEL_0 = 0;
    public final static int USER_LEVEL_1 = 1;
    public final static int USER_LEVEL_2 = 2;
    public final static int USER_LEVEL_3 = 3;
    public final static int USER_LEVEL_4 = 4;
    public final static int USER_LEVEL_5 = 5;
    public final static int USER_LEVEL_6 = 6;
    public final static int USER_LEVEL_7 = 7;
    public final static int USER_LEVEL_8 = 8;

    //会员等级--余额账户-佣金比例
    public static volatile Map<Integer, BigDecimal> LEVEL_BALANCE_COMMISSION_RATE = new HashMap();
    //会员等级--余额账户-10次任务收益
    public static volatile Map<Integer, BigDecimal> LEVEL_BALANCE_TASK_INCOME = new HashMap();
    //会员等级--余额账户-余额要求
    public static volatile Map<Integer, BigDecimal> LEVEL_BALANCE_MIN_BALANCE = new HashMap();

    //会员等级---增值账户-额外佣金比例
    public static volatile Map<Integer, BigDecimal> LEVEL_INCREMENT_COMMISSION_RATE = new HashMap();
    //会员等级---增值账户-额外50%收益
    public static volatile Map<Integer, BigDecimal> LEVEL_INCREMENT_OTHER_INCOME = new HashMap();

    //会员等级---提款限额
    public static volatile Map<Integer, BigDecimal> LEVEL_CASH_MAX_LIMIT = new HashMap();

    public static void main(String[] args) {
        System.out.println(LogicStaticContact.LEVEL_BALANCE_COMMISSION_RATE.get(0));
    }

    static {
        //提款限额
        LEVEL_CASH_MAX_LIMIT.put(0,new BigDecimal("5000"));
        LEVEL_CASH_MAX_LIMIT.put(1,new BigDecimal("5000"));
        LEVEL_CASH_MAX_LIMIT.put(2,new BigDecimal("5000"));
        LEVEL_CASH_MAX_LIMIT.put(3,new BigDecimal("10000"));
        LEVEL_CASH_MAX_LIMIT.put(4,new BigDecimal("30000"));
        LEVEL_CASH_MAX_LIMIT.put(5,new BigDecimal("100000"));
        LEVEL_CASH_MAX_LIMIT.put(6,new BigDecimal("300000"));
        LEVEL_CASH_MAX_LIMIT.put(7,new BigDecimal("500000"));
    }

    static {
        //增值账户-额外佣金比例
        LEVEL_INCREMENT_COMMISSION_RATE.put(0,new BigDecimal("0.0030"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(1,new BigDecimal("0.0035"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(2,new BigDecimal("0.0038"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(3,new BigDecimal("0.0040"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(4,new BigDecimal("0.0043"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(5,new BigDecimal("0.0045"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(6,new BigDecimal("0.0048"));
        LEVEL_INCREMENT_COMMISSION_RATE.put(7,new BigDecimal("0.0050"));
        //增值账户-额外50%收益
        LEVEL_INCREMENT_OTHER_INCOME.put(0,new BigDecimal("3"));
        LEVEL_INCREMENT_OTHER_INCOME.put(1,new BigDecimal("18"));
        LEVEL_INCREMENT_OTHER_INCOME.put(2,new BigDecimal("188"));
        LEVEL_INCREMENT_OTHER_INCOME.put(3,new BigDecimal("400"));
        LEVEL_INCREMENT_OTHER_INCOME.put(4,new BigDecimal("1275"));
        LEVEL_INCREMENT_OTHER_INCOME.put(5,new BigDecimal("4500"));
        LEVEL_INCREMENT_OTHER_INCOME.put(6,new BigDecimal("14250"));
        LEVEL_INCREMENT_OTHER_INCOME.put(7,new BigDecimal("15000"));
    }

    static {
        //余额账户-余额要求
        LEVEL_BALANCE_MIN_BALANCE.put(0,new BigDecimal("100"));
        LEVEL_BALANCE_MIN_BALANCE.put(1,new BigDecimal("500"));
        LEVEL_BALANCE_MIN_BALANCE.put(2,new BigDecimal("5000"));
        LEVEL_BALANCE_MIN_BALANCE.put(3,new BigDecimal("10000"));
        LEVEL_BALANCE_MIN_BALANCE.put(4,new BigDecimal("30000"));
        LEVEL_BALANCE_MIN_BALANCE.put(5,new BigDecimal("100000"));
        LEVEL_BALANCE_MIN_BALANCE.put(6,new BigDecimal("300000"));
        LEVEL_BALANCE_MIN_BALANCE.put(7,new BigDecimal("500000"));
        //余额账户--佣金比例
        LEVEL_BALANCE_COMMISSION_RATE.put(0,new BigDecimal("0.0060"));
        LEVEL_BALANCE_COMMISSION_RATE.put(1,new BigDecimal("0.0070"));
        LEVEL_BALANCE_COMMISSION_RATE.put(2,new BigDecimal("0.0075"));
        LEVEL_BALANCE_COMMISSION_RATE.put(3,new BigDecimal("0.0080"));
        LEVEL_BALANCE_COMMISSION_RATE.put(4,new BigDecimal("0.0085"));
        LEVEL_BALANCE_COMMISSION_RATE.put(5,new BigDecimal("0.0090"));
        LEVEL_BALANCE_COMMISSION_RATE.put(6,new BigDecimal("0.0095"));
        LEVEL_BALANCE_COMMISSION_RATE.put(7,new BigDecimal("0.0100"));
        //余额账户-10次任务收益
        LEVEL_BALANCE_TASK_INCOME.put(0,new BigDecimal("6"));
        LEVEL_BALANCE_TASK_INCOME.put(1,new BigDecimal("35"));
        LEVEL_BALANCE_TASK_INCOME.put(2,new BigDecimal("375"));
        LEVEL_BALANCE_TASK_INCOME.put(3,new BigDecimal("800"));
        LEVEL_BALANCE_TASK_INCOME.put(4,new BigDecimal("2550"));
        LEVEL_BALANCE_TASK_INCOME.put(5,new BigDecimal("9000"));
        LEVEL_BALANCE_TASK_INCOME.put(6,new BigDecimal("28500"));
        LEVEL_BALANCE_TASK_INCOME.put(7,new BigDecimal("50000"));
    }

    /**
     * @desc 根据余额获取最高等级
     * @author nada
     * @create 2021/5/12 10:54 下午
    */
    public static int getMaxUserLevelByTotalBalance(BigDecimal money){
        int tempMaxLevel = 0;
        for(Map.Entry<Integer, BigDecimal> entry : LEVEL_BALANCE_MIN_BALANCE.entrySet()){
            int key = entry.getKey();
            BigDecimal value = entry.getValue();
            if(CommonContact.isBigger(money,value) && tempMaxLevel < key){
                tempMaxLevel = key;
            }
        }
        return tempMaxLevel;
    }
}
