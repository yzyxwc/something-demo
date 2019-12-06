package com.example.redisdemo.annocation;

import com.example.redisdemo.enums.PreventStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解类实现接口防刷
 * @author wc
 * @date 2019-12-06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InterfaceLimit {
    /**
     * 限制的时间值（秒）
     *
     * @return
     */
    String value() default "60";

    /**
     * 提示
     */
    String message() default "";

    /**
     * 策略
     *
     * @return
     */
    PreventStrategy strategy() default PreventStrategy.DEFAULT;
}
