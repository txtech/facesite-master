package com.nabobsite.modules.nabob.api.model;

import com.jeesite.common.lang.ObjectUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @ClassName nada
 * @Date 2021/5/15 9:46 下午
 * @Version 1.0
 */
@ApiModel(value = "OrderInfoModel",description = "订单信息")
public class OrderInfoModel {

    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("订单类型")
    private Integer type;
    @ApiModelProperty("订单状态")
    private Integer orderStatus;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("上游订单号")
    private String porderNo;
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;
    @ApiModelProperty("真实到账金额")
    private BigDecimal actualMoney;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("订单IP")
    private String ipaddress;
    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @Override
    public Object clone() {
        return ObjectUtils.cloneBean(this);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPorderNo() {
        return porderNo;
    }

    public void setPorderNo(String porderNo) {
        this.porderNo = porderNo;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
