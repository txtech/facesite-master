package com.nabobsite.modules.nabob.api.model;

import com.jeesite.common.lang.ObjectUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName nada
 * @Date 2021/5/15 9:46 下午
 * @Version 1.0
 */
@ApiModel(value = "WarehouseTaskReqModel",description = "云仓库产品")
public class WarehouseTaskReqModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("云仓库ID")
    private String warehouseId;
    @ApiModelProperty("订单金额")
    private BigDecimal amount;
    @ApiModelProperty("请求IP")
    private String reqIp;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
