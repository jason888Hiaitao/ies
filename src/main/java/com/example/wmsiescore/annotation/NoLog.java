package com.example.wmsiescore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不记录日志注解
 * 用于标记不需要记录日志的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLog {
    /**
     * 不记录日志的原因
     */
    String value() default "";
}