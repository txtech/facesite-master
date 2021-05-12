package com.jeesite.common.utils.thread;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池jmx接口实现类
 */
public class ThreadPoolManager {

	private final ThreadPoolExecutor pool;

	public ThreadPoolManager(ThreadPoolExecutor pool) {
		this.pool = pool;
	}

	public int coreSize() {
		return pool.getCorePoolSize();
	}

	public int maxSize() {
		return pool.getMaximumPoolSize();
	}

	public int currSize() {
		return pool.getPoolSize();
	}

	public int largestSize() {
		return pool.getLargestPoolSize();
	}

	public int taskSize() {
		return pool.getQueue().size();
	}

	public void setCoreSize(int coreSize) {
		if (coreSize > 0) {
			pool.setCorePoolSize(coreSize);
		}
	}

	public void setMaxSize(int maxSize) {
		if (maxSize > 0) {
			pool.setMaximumPoolSize(maxSize);
		}
	}
}
