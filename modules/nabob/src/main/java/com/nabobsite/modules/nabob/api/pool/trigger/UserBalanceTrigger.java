package com.nabobsite.modules.nabob.api.pool.trigger;

import com.nabobsite.modules.nabob.api.pool.manager.TriggerOperation;
import com.nabobsite.modules.nabob.api.service.core.LogicService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import java.math.BigDecimal;

/**
 * @desc 用户余额触发
 * @author nada
 * @create 2021/5/12 5:41 下午
*/
public class UserBalanceTrigger extends TriggerOperation {

	private int type;
	private BigDecimal updateMoney;
	private LogicService logicService;

	public UserBalanceTrigger(String userId,int type,BigDecimal updateMoney, UserInfoDao userInfoDao,LogicService logicService) {
		super(userId,userInfoDao);
		this.type = type;
		this.userId = userId;
		this.updateMoney = updateMoney;
		this.logicService = logicService;
	}

	@Override
	public void execute() {
		logger.info("用户余额触发器，userId:{},type:{},money:{}",userId,type,updateMoney);
		UserInfo userInfo = logicService.getUserInfoByUserId(userId);
		if(userInfo == null){
			return;
		}
		UserAccount userAccount = logicService.getUserAccountByUserId(userId);
		if(userAccount == null){
			return;
		}

		boolean levelUpOK = logicService.memberLevelUp(userInfo,userAccount,type,updateMoney);
		logger.info("触发器会员升级:{},{}",userId,levelUpOK);

		boolean memberProfitOK = logicService.memberProfit(userInfo,userAccount,type,updateMoney);
		logger.info("触发器会员分润:{},{}",userId,memberProfitOK);

		boolean teamProfitOK = logicService.teamProfit(userInfo,userAccount,type,updateMoney);
		logger.info("触发器团队分润:{},{}",userId,teamProfitOK);
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
