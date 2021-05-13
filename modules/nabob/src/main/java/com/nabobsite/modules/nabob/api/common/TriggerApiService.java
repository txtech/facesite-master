package com.nabobsite.modules.nabob.api.common;

import com.nabobsite.modules.nabob.api.common.task.UserOrderTrigger;
import com.nabobsite.modules.nabob.api.common.task.UserRegisterTrigger;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerThread;
import com.nabobsite.modules.nabob.cms.order.dao.OrderDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName nada
 * @Date 2021/5/12 10:55 上午
 * @Version 1.0
 */
@Service
public class TriggerApiService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private TriggerPoolManagerImpl triggerPoolManager;

    /**
     * @desc 注册成功触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void registerTrigger(String userId){
        TriggerThread callback = new UserRegisterTrigger(userId);
        triggerPoolManager.submit(callback);
    }

    /**
     * @desc 用户充值触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void payOrderTrigger(String userId,String orderNo) {
        TriggerThread callback = new UserOrderTrigger(userId,orderNo,orderDao,userInfoDao,userAccountDao);
        triggerPoolManager.submit(callback);
    }
}
