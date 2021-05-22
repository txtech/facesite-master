package com.nabobsite.modules.nabob.config;

import com.nabobsite.modules.nabob.api.pool.TriggerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/14 6:59 下午
 * @Version 1.0
 */
@Component
public class InitializationCommandLine implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private TriggerApiService triggerApiService;

    @Override
    public void run(String... args){
        triggerApiService.systemStartTrigger();
    }

    /**
     * @desc 获取所有的bean名称
     * @author nada
     * @create 2021/5/14 7:57 下午
    */
    public void getAllBeans(){
        String[] beans = appContext.getBeanDefinitionNames();
		Arrays.sort(beans);
		for (String bean : beans) {
			System.out.println(bean + " of Type :: " + appContext.getBean(bean).getClass());
		}
    }
}
