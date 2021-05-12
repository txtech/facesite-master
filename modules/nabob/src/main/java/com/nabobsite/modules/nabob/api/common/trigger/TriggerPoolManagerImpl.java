package com.nabobsite.modules.nabob.api.common.trigger;

import cn.hutool.core.thread.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class TriggerPoolManagerImpl implements TriggerManager {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerPoolManagerImpl.class);

    private ThreadPoolExecutor executor;

    public TriggerPoolManagerImpl() {
        int corePoolSize = 10;
        int maxPoolSize = 200;
        int ildeTime = 60;
        int maxTaskNum = 1000;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(maxTaskNum);
        NamedThreadFactory factory = new NamedThreadFactory("triggerPool",false);
        executor = new ThreadPoolExecutor(corePoolSize,maxPoolSize, ildeTime, TimeUnit.SECONDS, workQueue, factory,new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public boolean submit(TriggerThread callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("submit callback player:"+ (callback == null ? "null" : callback.getUserId()));
        }
        if (callback != null) {
            executor.submit(callback);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @desc 根据用户Id取消关联的定时任务
     * @author nada
     * @create 2021/5/12 4:58 下午
    */
    @Override
    public boolean cancel(String userId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cancel callback userId:" + userId);
        }
        return false;
    }
}
