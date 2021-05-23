package com.nabobsite.modules.nabob.api.pool;

import com.nabobsite.modules.nabob.api.pool.trigger.InitLoadDbDataTrigger;
import com.nabobsite.modules.nabob.api.pool.trigger.UserBalanceTrigger;
import com.nabobsite.modules.nabob.api.pool.trigger.UserRegisterTrigger;
import com.nabobsite.modules.nabob.api.pool.manager.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.api.pool.manager.TriggerThread;
import com.nabobsite.modules.nabob.api.service.core.LogicService;
import com.nabobsite.modules.nabob.cms.sys.dao.SysI18nDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserAccountDao;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(TriggerApiService.class);
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private SysI18nDao sysI18nDao;
    @Autowired
    private LogicService logicService;
    @Autowired
    private TriggerPoolManagerImpl triggerPoolManager;

    /**
     * @desc 系统启动触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void systemStartTrigger() {
        logger.info("系统启动成功,开始加载数据到内存");
        TriggerThread callback = new InitLoadDbDataTrigger(sysI18nDao,userInfoDao);
        triggerPoolManager.submit(callback);
    }

    /**
     * @desc 注册成功触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void registerTrigger(String userId) {
        TriggerThread callback = new UserRegisterTrigger(userId,userInfoDao,logicService);
        triggerPoolManager.submit(callback);
    }

    /**
     * @desc 用户余额触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void balanceTrigger(String userId, int type, BigDecimal updateMoney) {
        TriggerThread callback = new UserBalanceTrigger(userId,type,updateMoney, userInfoDao, userAccountDao,logicService);
        triggerPoolManager.submit(callback);
    }
}
