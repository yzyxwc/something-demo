package com.example.redisdemo.annocation;

import com.example.redisdemo.enums.TypeEnum;

import java.lang.annotation.*;

/**
 * DesensitizedAnnotation 脱敏注解类
 * @author wc
 * @date 2019/10/29 09:50
 * @version 1.0
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DesensitizedAnnotation {
    /*脱敏数据类型(规则)*/
    TypeEnum type();
    /*判断注解是否生效，暂时没有用到*/
    String isEffictiveMethod() default "";

}