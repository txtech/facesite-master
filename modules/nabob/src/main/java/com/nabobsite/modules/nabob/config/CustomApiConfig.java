package com.nabobsite.modules.nabob.config;

import com.jeesite.modules.swagger.config.SwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 10:09 上午
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(name="web.swagger.enabled", havingValue="true", matchIfMissing=false)
public class CustomApiConfig {

    @Bean
    @ConditionalOnProperty(name="web.swagger.nabob.enabled", havingValue="true", matchIfMissing=true)
    public Docket customApi() {
        String moduleCode = "nabob";
        String moduleName = "富豪模块";
        String basePackage = "com.nabobsite.modules.nabob.api.web";
        return SwaggerConfig.docket(moduleCode, moduleName, basePackage)
                .select()
                .apis(
//						Predicates.and(Predicates.and(
//						RequestHandlerSelectors.withClassAnnotation(Api.class),
//						RequestHandlerSelectors.withMethodAnnotation(ResponseBody.class)),
//						RequestHandlerSelectors.basePackage(basePackage))
                        RequestHandlerSelectors.basePackage(basePackage)
                ).build();
    }

}
