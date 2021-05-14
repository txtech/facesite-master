package com.nabobsite.modules.nabob.api.common;

import com.nabobsite.modules.nabob.api.common.task.UserBalanceTrigger;
import com.nabobsite.modules.nabob.api.common.task.UserRegisterTrigger;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerThread;
import com.nabobsite.modules.nabob.api.dao.CustomUserInfoDao;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @ClassName nada
 * @Date 2021/5/12 10:55 上午
 * @Version 1.0
 */
@Service
public class TriggerApiService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserAccountApiService userAccountApiService;
    @Autowired
    private TriggerPoolManagerImpl triggerPoolManager;

    /**
     * @desc 注册成功触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void registerTrigger(String userId){
        TriggerThread callback = new UserRegisterTrigger(userId,userInfoDao,userAccountApiService,triggerPoolManager);
        triggerPoolManager.submit(callback);
    }

    /**
     * @desc 用户余额触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void balanceTrigger(String userId,BigDecimal payMoney) {
        TriggerThread callback = new UserBalanceTrigger(userId,payMoney,userInfoDao,userAccountApiService);
        triggerPoolManager.submit(callback);
    }
}
