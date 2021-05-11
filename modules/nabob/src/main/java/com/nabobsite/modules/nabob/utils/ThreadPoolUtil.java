package com.nabobsite.modules.nabob.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nada
 */
public class ThreadPoolUtil {
    /**
     * @desc 线程缓冲队列
     * @author nada
     * @create 2020/12/21 11:09 上午
    */
    private static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(100);
    /**
     * @desc 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
     * @author nada
     * @create 2020/12/21 11:09 上午
    */
    private static final int SIZE_CORE_POOL = 5;
    /**
     * @desc 线程池维护线程的最大数量
     * @author nada
     * @create 2020/12/21 11:09 上午
    */
    private static final int SIZE_MAX_POOL = 10;
    /**
     * @desc 线程池维护线程所允许的空闲时间
     * @author nada
     * @create 2020/12/21 11:11 上午
    */
    private static final long ALIVE_TIME = 2000;

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS, bqueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        pool.prestartAllCoreThreads();
    }

    public static ThreadPoolExecutor getPool() {
        return pool;
    }
}
