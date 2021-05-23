package com.nabobsite.modules.nabob.api.pool.trigger;

import com.nabobsite.modules.nabob.api.pool.manager.TriggerOperation;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
import com.nabobsite.modules.nabob.api.service.core.LogicService;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @desc 用户注册触发器
 * @author nada
 * @create 2021/5/12 5:40 下午
*/
public class UserRegisterTrigger extends TriggerOperation {

	private static Logger logger = LoggerFactory.getLogger(UserRegisterTrigger.class);

	private LogicService logicService;

	public UserRegisterTrigger(String userId,UserInfoDao userInfoDao,LogicService logicServic) {
		super(userId,userInfoDao);
		this.userId = userId;
		this.userInfoDao = userInfoDao;
		this.logicService = logicServic;
	}

	@Override
	public void execute() {
		logger.info("用户注册触发器，userId:{}",userId);
		UserInfo curUserInfo = logicService.getUserInfoByUserId(userId);
		if(curUserInfo == null){
			return;
		}
		String parent1UserId = curUserInfo.getParent1UserId();
		String parent2UserId = curUserInfo.getParent2UserId();
		String parent3UserId = curUserInfo.getParent3UserId();
		boolean isUpdateOk = logicService.updateTeam(parent1UserId,parent2UserId,parent3UserId);
		logger.info("注册触发器更新3级团队人数:{},{}",userId,isUpdateOk);
	}

	public LogicService getLogicService() {
		return logicService;
	}

	public void setLogicService(LogicService logicService) {
		this.logicService = logicService;
	}
}
