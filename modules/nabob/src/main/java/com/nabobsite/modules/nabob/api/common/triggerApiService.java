package com.nabobsite.modules.nabob.api.common;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/12 10:55 上午
 * @Version 1.0
 */
@Service
@Transactional(readOnly=true)
public class triggerApiService {

    /**
     * @desc 注册成功触发器
     * @author nada
     * @create 2021/5/11 10:33 下午
     */
    @Transactional (readOnly = false, rollbackFor = Exception.class)
    public void registerOkTrigger(String accountNo) {

    }
}
