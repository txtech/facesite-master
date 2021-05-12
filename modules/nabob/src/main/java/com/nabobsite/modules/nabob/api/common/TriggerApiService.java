package com.nabobsite.modules.nabob.api.common;

import com.nabobsite.modules.nabob.api.common.task.UserOrderTrigger;
import com.nabobsite.modules.nabob.api.common.task.UserRegisterTrigger;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerQueueManagerImpl;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName nada
 * @Date 2021/5/12 10:55 上午
 * @Version 1.0
 */
@Service
@Transactional(readOnly=true)
public class TriggerApiService {

    @Autowired
    private TriggerPoolManagerImpl timeOutManager;

    /**
     * @desc 注册成功触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void registerTrigger(String userId) {
        TriggerThread callback = new UserRegisterTrigger(userId);
        timeOutManager.submit(callback);
    }

    /**
     * @desc 用户充值触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void payOrderTrigger(String userId,Long orderNo) {
        TriggerThread callback = new UserOrderTrigger(userId,orderNo);
        timeOutManager.submit(callback);
    }
}
