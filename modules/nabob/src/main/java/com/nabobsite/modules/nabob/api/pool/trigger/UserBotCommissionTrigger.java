package com.nabobsite.modules.nabob.api.pool.trigger;

import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.pool.manager.TriggerOperation;
import com.nabobsite.modules.nabob.api.service.core.LogicService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

import java.math.BigDecimal;

/**
 * @desc 用户刷单佣金触发
 * @author nada
 * @create 2021/5/12 5:41 下午
*/
public class UserBotCommissionTrigger extends TriggerOperation {

	private BigDecimal updateMoney;
	private LogicService logicService;

	public UserBotCommissionTrigger(String userId, BigDecimal updateMoney, UserInfoDao userInfoDao, LogicService logicService) {
		super(userId,userInfoDao);
		this.userId = userId;
		this.updateMoney = updateMoney;
		this.logicService = logicService;
	}

	@Override
	public void execute() {
		logger.info("用户刷单佣金触发器，userId:{},type:{},money:{}",userId,updateMoney);
		UserInfo userInfo = logicService.getUserInfoByUserId(userId);
		if(userInfo == null){
			return;
		}
		UserAccount userAccount = logicService.getUserAccountByUserId(userId);
		if(userAccount == null){
			return;
		}
		String title = "动态收益分润";
		int type = ContactUtils.USER_PROFIT_TYPE_1;
		Boolean isProfitOk  = logicService.memberProfit(title,type,userInfo,userAccount,updateMoney);
		logger.info("用户刷单佣金触发器分润:{},{}",userId,isProfitOk);
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getUpdateMoney() {
		return updateMoney;
	}

	public void setUpdateMoney(BigDecimal updateMoney) {
		this.updateMoney = updateMoney;
	}
}
