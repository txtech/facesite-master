package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;

/**
 * @desc 用户支付触发
 * @author nada
 * @create 2021/5/12 5:41 下午
*/
public class UserOrderTrigger extends TriggerOperation {

	private String userId;
	private Long orderNo;

	public UserOrderTrigger(String userId, Long orderNo) {
		super(userId);
		this.orderNo = orderNo;
	}

	@Override
	public void execute() {
		LOG.info("用户支付触发，userId:{},orderNo:{}",userId,orderNo);
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
}
