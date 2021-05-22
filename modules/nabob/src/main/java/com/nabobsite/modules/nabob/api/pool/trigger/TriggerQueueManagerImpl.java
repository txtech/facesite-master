package com.nabobsite.modules.nabob.api.pool.trigger;
import java.util.concurrent.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import cn.hutool.core.thread.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TriggerQueueManagerImpl implements TriggerManager {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerQueueManagerImpl.class);

    DelayQueue<DelayItem> queue = new DelayQueue<DelayItem>();
    ConcurrentHashMap<String, DelayItem> pdMap = new ConcurrentHashMap<String, DelayItem>();
    private Thread monitor;
    private ThreadPoolExecutor executor;

    public TriggerQueueManagerImpl() {
        int corePoolSize = 10;
        int maxPoolSize = 200;
        int ildeTime = 60;
        int maxTaskNum = 1000;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(maxTaskNum);
        NamedThreadFactory factory = new NamedThreadFactory("triggerQueue",false);
        executor = new ThreadPoolExecutor(corePoolSize,maxPoolSize, ildeTime, TimeUnit.SECONDS, workQueue, factory,new ThreadPoolExecutor.CallerRunsPolicy());
        monitor = new Monitor();
        monitor.setDaemon(true);
        monitor.start();
    }

    @Override
    public boolean submit(TriggerThread callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("submit callback player:"+ (callback == null ? "null" : callback.getUserId()));
        }
        if (callback != null) {
            DelayItem item = new DelayItem(callback);
            pdMap.put(callback.getUserId(), item);
            return queue.add(item);
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
        DelayItem item = pdMap.remove(userId);
        if (item != null) {
            return queue.remove(item);
        } else {
            return false;
        }
    }

    /**
     * @desc 守护进程监控任务
     * @author nada
     * @create 2021/5/12 4:38 下午
    */
    private class Monitor extends Thread {
        @Override
        public void run() {
            /*while (true) {
                try {
                    DelayItem item  = queue.take();
                    if (item != null && item.getRunnable() != null) {
                        pdMap.remove(item.getRunnable().getUserId());
                        executor.submit(item.getRunnable());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    private static class DelayItem implements Delayed {
        private TriggerThread callback;
        private final long future;

        private DelayItem(TriggerThread callback) {
            this.callback = callback;
            this.future = TimeUnit.NANOSECONDS.convert(callback.getTimeOut(),TimeUnit.SECONDS) + System.nanoTime();
        }
        @Override
        public int compareTo(Delayed di) {
            if (di != null && di instanceof DelayItem) {
                DelayItem temp = (DelayItem) di;
                if (future > temp.getSubmitTime()) {
                    return 1;
                } else if (future == temp.getSubmitTime()) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return 1;
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long debug = unit.convert(future - System.nanoTime(),TimeUnit.NANOSECONDS);
            return debug;
        }

        public TriggerThread getRunnable() {
            return callback;
        }

        public long getSubmitTime() {
            return future;
        }
    }

}
