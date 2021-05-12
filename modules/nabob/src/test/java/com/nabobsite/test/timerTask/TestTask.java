package com.nabobsite.test.timerTask;

import java.util.Timer;
import java.util.TimerTask;

public class TestTask extends TimerTask{

    private Timer time;
    private String userId;
    private int freeze; // 冻结
    public TestTask(Timer time,String userId,int freeze){
        this.time=time;
        this.userId=userId;
        this.freeze = freeze;
         time.schedule(this, 4*1000);
    }

    @Override
    public void run() {
        if(freeze==1){
            System.out.println("系统超时，执行完结束");
            time.cancel();
        }else{
            System.out.println("执行其他任务，执行完结束");
            time.cancel();
        }
    }
}

