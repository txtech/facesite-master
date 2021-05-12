package com.jeesite.common.utils.thread;

/**
 * 线程池jmx接口
 */
public interface ThreadPoolManagerMBean {

	/**
	 * 获取核心线程数
	 *
	 * @return
	 */
	public int coreSize();

	/**
	 * 获取最大线程数
	 *
	 * @return
	 */
	public int maxSize();

	/**
	 * 当前线程数
	 *
	 * @return
	 */
	public int currSize();

	/**
	 * 历史最大线程数
	 *
	 * @return
	 */
	public int largestSize();

	/**
	 * 当前任务队列大小
	 *
	 * @return
	 */
	public int taskSize();

	/**
	 * 设置线程池核心线程数
	 *
	 * @param coreSize
	 */
	public void setCoreSize(int coreSize);

	/**
	 * 设置线程池最大线程数
	 *
	 * @param maxSize
	 */
	public void setMaxSize(int maxSize);
}
