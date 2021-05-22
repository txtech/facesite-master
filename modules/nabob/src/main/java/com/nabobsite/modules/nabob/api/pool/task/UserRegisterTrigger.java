package com.nabobsite.modules.nabob.api.pool.task;

import com.nabobsite.modules.nabob.api.pool.trigger.TriggerOperation;
import com.nabobsite.modules.nabob.api.common.ContactUtils;
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

	public UserRegisterTrigger(String userId,UserInfoDao userInfoDao) {
		super(userId,userInfoDao);
		this.userId = userId;
		this.userInfoDao = userInfoDao;
	}

	@Override
	public void execute() {
		logger.info("用户注册触发器，userId:{}",userId);
		UserInfo curUserInfo = this.getUserInfoByUserId(userId);
		if(curUserInfo == null){
			return;
		}
		String parent1UserId = curUserInfo.getParent1UserId();
		String parent2UserId = curUserInfo.getParent2UserId();
		String parent3UserId = curUserInfo.getParent3UserId();
		this.updateTeam(parent1UserId,parent2UserId,parent3UserId);
	}

	/**
	 * @desc 修改三级团队人数
	 * @author nada
	 * @create 2021/5/11 2:55 下午
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean updateTeam(String parent1UserId,String parent2UserId,String parent3UserId) {
		try {
			//增加1人
			int num = 1;
			if(ContactUtils.isOkUserId(parent1UserId)){
				UserInfo userInfo = new UserInfo();
				userInfo.setId(parent1UserId);
				userInfo.setTeam1Num(num);
				long dbResult = userInfoDao.updateTeamNum(userInfo);
				if(ContactUtils.dbResult(dbResult)){
					logger.info("修改1级团队人数:{}",parent1UserId);
				}
			}
			if(ContactUtils.isOkUserId(parent2UserId)){
				UserInfo userInfo = new UserInfo();
				userInfo.setId(parent2UserId);
				userInfo.setTeam2Num(num);
				long dbResult = userInfoDao.updateTeamNum(userInfo);
				if(ContactUtils.dbResult(dbResult)){
					logger.info("修改2级团队人数:{}",parent2UserId);
				}
			}
			if(ContactUtils.isOkUserId(parent3UserId)){
				UserInfo userInfo = new UserInfo();
				userInfo.setId(parent3UserId);
				userInfo.setTeam3Num(num);
				long dbResult = userInfoDao.updateTeamNum(userInfo);
				if(ContactUtils.dbResult(dbResult)){
					logger.info("修改3级团队人数:{}",parent3UserId);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("修改团队人数异常",e);
			return false;
		}
	}
}
