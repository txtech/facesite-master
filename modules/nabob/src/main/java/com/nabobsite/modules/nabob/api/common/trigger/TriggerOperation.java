package com.nabobsite.modules.nabob.api.common.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TriggerOperation implements TriggerThread {
	protected static final Logger LOG = LoggerFactory .getLogger(TriggerOperation.class);
	protected String userId;
	protected long timeout;

	public TriggerOperation(String userId) {
		this.userId = userId;
		this.timeout = 15;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (Exception e) {
			LOG.warn("执行超时回调函数失败！", e);
		}
	}

	public abstract void execute();

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public long getTimeOut() {
		return timeout;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
