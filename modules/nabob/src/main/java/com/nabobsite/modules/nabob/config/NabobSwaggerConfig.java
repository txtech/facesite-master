package com.nabobsite.modules.nabob.config;

import com.jeesite.modules.swagger.config.SwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.function.Predicate;

import com.google.common.base.Optional;
import com.google.common.base.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/5/11 10:09 上午
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(name="web.swagger.enabled", havingValue="true", matchIfMissing=false)
public class NabobSwaggerConfig {

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
