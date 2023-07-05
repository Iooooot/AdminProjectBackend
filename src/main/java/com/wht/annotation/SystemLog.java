package com.wht.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 日志标志注解(加了该注解会使用aop进行日志处理)
 * @author wht
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface SystemLog {
    /**
     *业务的简单描述
     */
    @AliasFor("value")
    String businessName() default "";

    @AliasFor("businessName")
    String value() default "";
}
