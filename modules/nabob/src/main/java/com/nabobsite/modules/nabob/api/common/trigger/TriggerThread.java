package com.nabobsite.modules.nabob.api.common.trigger;

public interface TriggerThread extends Runnable{

    public String getUserId();

    public long getTimeOut();

    @Override
    public void run();
}
