package com.nabobsite.modules.nabob.api.pool.trigger;

public interface TriggerThread extends Runnable{

    public String getUserId();

    public long getTimeOut();

    @Override
    public void run();
}
