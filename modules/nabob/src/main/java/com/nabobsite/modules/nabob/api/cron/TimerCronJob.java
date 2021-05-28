/**
 * @类名称:OnlinePayJob.java
 * @时间:2017年11月14日下午12:06:46
 * @版权:版权所有 Copyright (c) 2017
 */
package com.nabobsite.modules.nabob.api.cron;

import com.nabobsite.modules.nabob.api.service.ProductApiService;
import com.nabobsite.modules.nabob.api.service.UserTaskApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @描述:定时任务
 * @时间:2017年11月14日 下午12:06:46
 */
@Component
@Lazy(false)
public class TimerCronJob {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductApiService productApiService;
    @Autowired
    private UserTaskApiService userTaskApiService;

    /**
     * @描述:定时更新（3分钟/次）
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void autodoJob3(){
        try {
            logger.info("3分钟定时更新开始");
            Boolean isOK = productApiService.doAiBotTaskJob();
            logger.info("3分钟定时更新结束,{}",isOK);
        } catch (Exception e) {
            logger.error("定时更新结束异常", e);
        }
    }

    /**
     * @描述:定时更新（15分钟/次）
     */
    @Scheduled(cron = "0 */15 * * * ?")
    public void autodoUserTaskJob(){
        try {
            logger.info("15分钟定时更新开始");
            Boolean isOK = userTaskApiService.doUserTaskJob();
            logger.info("15分钟定时更新结束,{}",isOK);
        } catch (Exception e) {
            logger.error("定时更新结束异常", e);
        }
    }

    /**
     * @描述:凌晨更新
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void autodoBotTaskJob(){
        try {
            logger.info("凌晨更新定时更新开始");
            Boolean isOK = productApiService.doBotCleanTaskJob();
            logger.info("凌晨更新定时更新结束,{}",isOK);
        } catch (Exception e) {
            logger.error("定时更新结束异常", e);
        }
    }
}
