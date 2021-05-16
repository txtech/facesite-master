package com.nabobsite.modules.nabob.api.model.req;

import com.jeesite.common.lang.ObjectUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName nada
 * @Date 2021/5/15 9:46 下午
 * @Version 1.0
 */
@ApiModel(value = "BotTaskReqModel",description = "无人机产品")
public class BotTaskReqModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("无人机ID")
    private String botId;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("请求IP")
    private String reqIp;

    @Override
    public Object clone() {
        return ObjectUtils.cloneBean(this);
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }
}
