package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;
import com.nabobsite.modules.nabob.api.entity.CommonStaticContact;
import com.nabobsite.modules.nabob.api.entity.TaskLogicStaticContact;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.order.entity.Order;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @desc 用户支付触发
 * @author nada
 * @create 2021/5/12 5:41 下午
*/
public class UserOrderTrigger extends TriggerOperation {

	private String orderNo;
	private OrderDao orderDao;
	private UserInfoDao userInfoDao;
	private UserAccountDao userAccountDao;

	public UserOrderTrigger(String userId, String orderNo,OrderDao orderDao,UserInfoDao userInfoDao,UserAccountDao userAccountDao) {
		super(userId);
		this.userId = userId;
		this.orderNo = orderNo;
		this.orderDao = orderDao;
		this.userInfoDao = userInfoDao;
		this.userAccountDao = userAccountDao;
	}

	@Override
	public void execute() {
		LOG.info("用户支付触发，userId:{},orderNo:{}",userId,orderNo);
		UserInfo userInfo = this.getUserInfoByUserId(userId);
		if(userInfo == null){
			LOG.error("用户支付触发,用户为空:{}",userId);
			return;
		}
		UserAccount userAccount = this.getUserAccountByUserId(userId);
		if(userInfo == null){
			LOG.error("用户支付触发,用户账户为空:{}",userId);
			return;
		}
		Order order = this.getOrderByOrderNo(orderNo);
		if(order == null){
			LOG.error("用户支付触发,订单为空:{}",orderNo);
			return;
		}
		int oldLock = userInfo.getLock();
		int currentLevel = userInfo.getLevel();
		BigDecimal payMoney = order.getPayMoney();
		BigDecimal currentTotalMoney = userAccount.getTotalMoney();
		int maxLevel = TaskLogicStaticContact.getMaxUserLevelByTotalBalance(currentTotalMoney);
		if(currentLevel < maxLevel){
			currentLevel = maxLevel;
			int userLock = this.getUserLock(currentLevel,payMoney);
			Boolean isUpLevelOk = this.updateUpLevel(userId,maxLevel,userLock);
			LOG.info("用户支付触发,用户升级且解锁:{},{}",userId,isUpLevelOk);
		}else{
			int userLock = this.getUserLock(currentLevel,payMoney);
			if(oldLock != CommonStaticContact.USER_LOCK_1 && userLock == CommonStaticContact.USER_LOCK_1){
				Boolean isUpLevelOk = this.updateLock(userId,userLock);
				LOG.info("用户支付触发,用户不升级只解锁:{},{}",userId,isUpLevelOk);
			}
		}
	}

	/**
	 * @desc 获取用户解锁状态：1：解锁 2：锁定
	 * @author nada
	 * @create 2021/5/12 11:22 下午
	*/
	public int getUserLock(int currentLevel,BigDecimal payMoney){
		if(currentLevel == TaskLogicStaticContact.USER_LEVEL_0 || currentLevel == TaskLogicStaticContact.USER_LEVEL_1){
			return 1;
		}
		BigDecimal mustBalance = TaskLogicStaticContact.LEVEL_BALANCE_MIN_BALANCE.get(currentLevel);
		if(CommonStaticContact.isBiggerOrEqual(payMoney,mustBalance)){
			return 1;
		}
		return 2;
	}

	/**
	 * @desc 根据账号ID升级
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public Boolean updateUpLevel(String userId,int upLevel,int userLock) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			userInfo.setLevel(upLevel);
			long dbResult = userInfoDao.update(userInfo);
			return CommonStaticContact.dbResult(dbResult);
		} catch (Exception e) {
			LOG.error("根据账号ID升级异常",e);
			return null;
		}
	}
	/**
	 * @desc 根据账号ID解锁
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public Boolean updateLock(String userId,int userLock) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setLock(userLock);
			long dbResult = userInfoDao.update(userInfo);
			return CommonStaticContact.dbResult(dbResult);
		} catch (Exception e) {
			LOG.error("根据账号ID升级异常",e);
			return null;
		}
	}

	/**
	 * @desc 根据订单号获取
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public Order getOrderByOrderNo(String orderNo) {
		try {
			if(StringUtils.isEmpty(orderNo)){
				return null;
			}
			Order order = new Order();
			order.setOrderNo(orderNo);
			return orderDao.getByEntity(order);
		} catch (Exception e) {
			LOG.error("根据订单号获取异常",e);
			return null;
		}
	}
	/**
	 * @desc 根据账号ID获取账户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UserAccount getUserAccountByUserId(String userId) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserAccount userAccount = new UserAccount();
			userAccount.setUserId(userId);
			return userAccountDao.getByEntity(userAccount);
		} catch (Exception e) {
			LOG.error("根据账号ID获取账户信息异常",e);
			return null;
		}
	}
	/**
	 * @desc 根据账号ID获取用户信息
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		try {
			if(StringUtils.isEmpty(userId)){
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(userId);
			return userInfoDao.getByEntity(userInfo);
		} catch (Exception e) {
			LOG.error("根据账号ID获取用户信息异常",e);
			return null;
		}
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
