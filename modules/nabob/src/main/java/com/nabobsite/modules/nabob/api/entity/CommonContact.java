package com.nabobsite.modules.nabob.api.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:25 上午
 * @Version 1.0
 */
public class CommonContact {

    public final static String AUTHORIZATION = "Authorization";
    public final static String TOKEN = "token";
    public final static String USERID = "userId";
    public final static String LANG = "lang";

    public final static BigDecimal ZERO = new BigDecimal("0");

    public final static String SYS_KEY_COUNTDOWN_TIME  = "sys.key.countdown.time";

    //用户状态
    public final static int USER_STATUS_1 = 1;//正常
    public final static int USER_STATUS_3 = 3;//禁用

    //用户解锁状态
    public final static int USER_LOCK_1 = 1;//正常
    public final static int USER_LOCK_2 = 2;//锁定

    //账户明细类型
    public final static int USER_ACCOUNT_DETAIL_TYPE_1 = 1;//余额账户
    public final static int USER_ACCOUNT_DETAIL_TYPE_2 = 20;//佣金账户
    public final static int USER_ACCOUNT_DETAIL_TYPE_3 = 30;//仓库资金
    public final static int USER_ACCOUNT_DETAIL_TYPE_4 = 40;//奖励账户

    //账户明细标题
    public final static String USER_ACCOUNT_DETAIL_TITLE_1 = "订单充值";
    public final static String USER_ACCOUNT_DETAIL_TITLE_2 = "注册奖励";
    public final static String USER_ACCOUNT_DETAIL_TITLE_3 = "任务奖励";
    public final static String USER_ACCOUNT_DETAIL_TITLE_4 = "刷单佣金";

    //奖励明细类型
    public final static int USER_ACCOUNT_REWARD_TYPE_1 = 1;//注册奖励
    public final static int USER_ACCOUNT_REWARD_TYPE_2 = 2;//新手任务奖励： 分享好友奖励,观看视频奖励,邀请好友奖励,定期投资奖励

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

    //用户任务 1:未开始 2:进行中 3:完成
    public final static int USER_TASK_STATUS_1 = 1;
    public final static int USER_TASK_STATUS_2 = 2;
    public final static int USER_TASK_STATUS_3 = 3;

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
    public static Boolean isEqualZero(BigDecimal num1){
        if(num1 == null){
            return false;
        }
        return isEqual(num1,ZERO);
    }

    /**
     * @desc userId是否合法
     * @author nada
     * @create 2021/5/15 11:25 下午
    */
    public static Boolean isOkUserId(String userId) {
        try {
            if(StringUtils.isEmpty(userId) || "0".equalsIgnoreCase(userId)){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 给时间加上几个小时
     * @return
     */
    public static Date addDateHour(int hour){
        try {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR,hour);
            date = cal.getTime();
            return date;
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //System.out.println("after:" + format.format(date));
            //return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
