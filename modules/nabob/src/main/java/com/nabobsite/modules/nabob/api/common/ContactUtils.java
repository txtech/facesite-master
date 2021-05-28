package com.nabobsite.modules.nabob.api.common;

import com.alibaba.fastjson.JSONObject;
import com.nabobsite.modules.nabob.api.common.response.ResultCode;
import com.nabobsite.modules.nabob.cms.user.entity.UserProfitDetail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 11:25 上午
 * @Version 1.0
 */
public class ContactUtils {

    private static final Logger logger = LoggerFactory.getLogger(ContactUtils.class);

    public final static BigDecimal ZERO = new BigDecimal("0");
    public final static BigDecimal HOUR = new BigDecimal("24");
    public final static BigDecimal MINUTE = new BigDecimal("60");
    public final static BigDecimal SECOND = new BigDecimal("60");
    public static String CHART_UTF ="UTF-8";
    public final static String LANG = "lang";
    public final static String TOKEN = "token";
    public final static String USERID = "userId";
    public final static String AUTHORIZATION = "Authorization";

    //用户升级人数
    public final static int USER_LEVEL_UP_TEAM_NUM = 5;
    //用户注册送现金
    public final static BigDecimal USER_REGISTER_REWARD = new BigDecimal("100");
    //用户注册奖励
    public final static BigDecimal USER_TACK_BASE_MONEY = new BigDecimal("990");

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

    //用户状态
    public final static int USER_STATUS_1 = 1;//正常
    public final static int USER_STATUS_3 = 3;//禁用

    //用户状态
    public final static int USER_VALID_1 = 1;//有效
    public final static int USER_VALID_2 = 2;//未生效

    //用户解锁状态
    public final static int USER_LOCK_1 = 1;//正常
    public final static int USER_LOCK_2 = 2;//锁定

    //用户任务 1:未开始 2:进行中 3:完成
    public final static int USER_TASK_STATUS_1 = 1;
    public final static int USER_TASK_STATUS_2 = 2;
    public final static int USER_TASK_STATUS_3 = 3;

    //用户云仓库日志类型
    public final static int WAREHOUSE_TYPE_1 = 1;//个人
    public final static int WAREHOUSE_TYPE_2 = 2;//团队

    //用户云仓库产品类型
    public final static int WAREHOUSE_PRODUCT_TYPE_1 = 1;//随存随取
    public final static int WAREHOUSE_PRODUCT_TYPE_2 = 2;//定投

    //云仓库操作类型
    public final static int WAREHOUSE_RECORD_TYPE_1 = 1;//定投
    public final static int WAREHOUSE_RECORD_TYPE_2 = 2;//撤资
    public final static int WAREHOUSE_RECORD_TYPE_3 = 3;//收益提现

    //账户明细类型
    public final static int USER_ACCOUNT_DETAIL_TYPE_1 = 1;//订单充值
    public final static int USER_ACCOUNT_DETAIL_TYPE_2 = 2;//新用户注册送奖励
    public final static int USER_ACCOUNT_DETAIL_TYPE_20 = 20;//佣金账户
    public final static int USER_ACCOUNT_DETAIL_TYPE_30 = 30;//仓库资金
    public final static int USER_ACCOUNT_DETAIL_TYPE_40 = 40;//奖励账户
    public final static int USER_ACCOUNT_DETAIL_TYPE_50 = 50;//分润收益
    public final static int USER_ACCOUNT_DETAIL_TYPE_60 = 60;//团队领取奖励

    //账户明细标题
    public final static String USER_ACCOUNT_DETAIL_TITLE_1 = "用户充值";
    public final static String USER_ACCOUNT_DETAIL_TITLE_2 = "注册奖励";
    public final static String USER_ACCOUNT_DETAIL_TITLE_3 = "任务奖励";
    public final static String USER_ACCOUNT_DETAIL_TITLE_4 = "刷单佣金";
    public final static String USER_ACCOUNT_DETAIL_TITLE_5 = "动态收益";

    //奖励明细类型
    public final static int USER_ACCOUNT_REWARD_TYPE_1 = 1;//分享好友奖励
    public final static int USER_ACCOUNT_REWARD_TYPE_2 = 2;//观看视频奖励
    public final static int USER_ACCOUNT_REWARD_TYPE_3 = 3;//邀请好友奖励
    public final static int USER_ACCOUNT_REWARD_TYPE_4 = 4;//定期投资奖励
    //奖励明细标题
    public final static String USER_ACCOUNT_REWARD_TITLE_1 = "分享好友奖励";
    public final static String USER_ACCOUNT_REWARD_TITLE_2 = "观看视频奖励";
    public final static String USER_ACCOUNT_REWARD_TITLE_3 = "邀请好友奖励";
    public final static String USER_ACCOUNT_REWARD_TITLE_4 = "定期投资奖励";

    //用户账户状态
    public final static int USER_ACCOUNT_STATUS_1 = 1;//正常
    public final static int USER_ACCOUNT_STATUS_2 = 2;//冻结

    //分润类型
    public final static int USER_PROFIT_TYPE_1 = 1;//佣金分润
    public final static int USER_PROFIT_TYPE_2 = 2;//充值分润
    public final static int USER_PROFIT_TYPE_3 = 3;//云仓库分润

    //订单类型
    public final static int ORDER_TYPE_RECHANGE = 1;//充值

    //团队奖励领取状态
    public final static int TEAM_USER_REWARD_STATUS_1 = 1;//未领取
    public final static int TEAM_USER_REWARD_STATUS_2 = 2;//已经领取

    /**
     * @desc 订单支付类型
     * @author nada
     * @create 2021/5/18 4:36 下午
    */
    public final static int ORDER_PAY_TYPE_1 = 1;//转卡支付
    public final static int ORDER_PAY_TYPE_2 = 2;//扫码支付

    //支付订单类型
    //1: 待支付 2:支付中 3:支付失败 4:支付成功 9:退款
    public final static int ORDER_STATUS_1 = 1;
    public final static int ORDER_STATUS_2 = 2;
    public final static int ORDER_STATUS_3 = 3;
    public final static int ORDER_STATUS_4 = 4;
    public final static int ORDER_STATUS_9 = 9;

    //出款类型
    //1: 待支付 2:支付中 3:支付失败 4:支付成功 9:退款
    public final static int CASH_STATUS_1 = 1;
    public final static int CASH_STATUS_2 = 2;
    public final static int CASH_STATUS_3 = 3;
    public final static int CASH_STATUS_4 = 4;
    public final static int CASH_STATUS_9 = 9;

    public final static Set<String> PHONE_NUM_AREA_CODE  =  new HashSet<>();
    static {
        PHONE_NUM_AREA_CODE.add("74");
        PHONE_NUM_AREA_CODE.add("91");
        PHONE_NUM_AREA_CODE.add("96");
        PHONE_NUM_AREA_CODE.add("99");
    }

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
    public static Boolean isLesserOrEqualZero(BigDecimal num1){
        if(num1 == null){
            return true;
        }
        if(num1.compareTo(ZERO) < 1){
            return true;
        }
        return false;
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

    public static BigDecimal parsePercentage(String amount) {
        try {
            if(StringUtils.isEmpty(amount)){
                return new BigDecimal("0");
            }
            if(amount.contains("%")){
                return new BigDecimal(amount.replaceAll("%","")).divide(new BigDecimal("100"));
            }
            return new BigDecimal(amount);
        } catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal("0");
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //成功返回
    public static JSONObject successMsg(String msg){
        JSONObject result = new JSONObject();
        result.put("code",ResultCode.SUCCESS.getCode());
        if(StringUtils.isEmpty(msg)){
            result.put("message",ResultCode.SUCCESS.getMsg());
        }else{
            result.put("message",msg);
        }
        return result;
    }

    //失败返回
    public static JSONObject failedMsg(String msg){
        JSONObject result = new JSONObject();
        result.put("code",ResultCode.FAILED.getCode());
        if(StringUtils.isEmpty(msg)){
            result.put("message",ResultCode.FAILED.getMsg());
        }else{
            result.put("message",msg);
        }
        return result;
    }

    /**
     * @描述: 是否响应成功
     * @作者:nada
     * @时间:2019/3/18
     **/
    public static boolean isOkResult (JSONObject result) {
        try {
            if(result == null){
                return false;
            }
            if(!result.containsKey ("code")){
                return false;
            }
            String code = result.getString ("code");
            if (String.valueOf(ResultCode.SUCCESS.getCode()).equals(code)){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("异常 ",e);
            return  false;
        }
    }

    /**
     * @描述:初始化设置报文请求响应编码格式
     * @时间:2017年12月18日 下午5:45:02
     */
    public static void initHttpServletRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request != null) {
                request.setCharacterEncoding(ContactUtils.CHART_UTF);
            }
            if (response != null) {
                response.setCharacterEncoding(ContactUtils.CHART_UTF);
            }
        } catch (Exception e) {
            logger.error("初始化设置报文请求响应编码格式异常", e);
        }
    }

    /**
     * @描述:网关响应报文
     * @作者:nada
     * @时间:2019/3/15
     **/
    public static ModelAndView writeResponse(HttpServletResponse response, JSONObject result) {
        try {
            initHttpServletRequest (null, response);
            response.setContentType("application/json;charset=utf-8");
            if(result == null || result.isEmpty ()){
                response.getWriter().write(result.toString());
                return null;
            }
            response.getWriter().write(result.toString());
        } catch (Exception e) {
            logger.error("Gateway write response exception", e);
        }
        return null;
    }

    /**
     * @desc 获取json
     * @author nada
     * @create 2021/5/21 1:57 下午
    */
    public static JSONObject str2JSONObject(String str) {
        try {
            if(StringUtils.isEmpty(str)){
                return new JSONObject();
            }
            JSONObject obj = JSONObject.parseObject(str);
            return obj;
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
