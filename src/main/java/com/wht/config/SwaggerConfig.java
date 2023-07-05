package com.wht.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wht
 * @date 2022/9/29 19:43
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enabled}")
    private Boolean enabled;
    @Value("${swagger.basePackage}")
    private String basePackage;
    private final String tokenHeader = "token";

    @Bean
    public Docket docket(){
        //指定swagger版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket.enable(enabled)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.regex("^(?!/error).*"))
                .paths(PathSelectors.any())
                .build()//添加登陆认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

        return docket;
    }


    /**
     * 认证的安全上下文
     */
    private List<SecurityScheme> securitySchemes() {
        //设置请求头信息
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        ApiKey apiKey = new ApiKey(tokenHeader, tokenHeader, "header");
        securitySchemes.add(apiKey);
        return securitySchemes;
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(getContextByPath());
        return securityContexts;
    }

    private SecurityContext getContextByPath() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                // 除了/user/login外其他要带上token
                .forPaths(PathSelectors.regex("^(?!/user/login).*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        securityReferences.add(new SecurityReference(tokenHeader, authorizationScopes));
        return securityReferences;
    }

    /**
     * api帮助文档的描述信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .contact( //主体内容：发布者名称、发布者网站地址、发布者邮箱
                        new Contact("wht",
                                "https://iooooot.github.io/",
                                "1369281736@qq.com")
                )
                .description("一个简单且易上手的 SpringBoot 后台管理模板")
                .title("MyAdmin 接口文档")
                .version("1.0")
                .build();
    }
}
