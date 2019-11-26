package com.example.redisdemo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * @author wc
 */
@Aspect
@Component
public class AopConfig {
    @Pointcut("execution(* com.example.redisdemo.controller..*.*(..))")
    public  void executeService(){

    }

    @Before("executeService()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }

    }
    @After("executeService()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().toString()+":"+joinPoint.getSignature().getName());
    }
}
