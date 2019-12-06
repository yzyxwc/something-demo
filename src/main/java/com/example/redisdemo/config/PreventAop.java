package com.example.redisdemo.config;

import com.alibaba.fastjson.JSON;
import com.example.redisdemo.annocation.InterfaceLimit;
import com.example.redisdemo.enums.PreventStrategy;
import com.example.redisdemo.exception.BusinessSilentException;
import com.example.redisdemo.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Base64;

/**
 * aop 实现 注解接口防刷
 * @author wc
 */
@Aspect
@Component
public class PreventAop {
    private static Logger log = LoggerFactory.getLogger(PreventAop.class);

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 切入点
     */
    @Pointcut("@annotation(com.example.redisdemo.annocation.InterfaceLimit)")
    public void pointcut() {
    }


    /**
     * 处理前
     *
     * @return
     */
    @Before("pointcut()")
    public void joinPoint(JoinPoint joinPoint) throws Exception {
        String requestStr ="prevent";
        if(joinPoint.getArgs().length != 0){
            requestStr = JSON.toJSONString(joinPoint.getArgs());
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),
                methodSignature.getParameterTypes());

        InterfaceLimit preventAnnotation = method.getAnnotation(InterfaceLimit.class);
        String methodFullName = method.getDeclaringClass().getName() + method.getName();

        entrance(preventAnnotation, requestStr,methodFullName);
        return;
    }


    /**
     * 入口
     *
     * @param prevent
     * @param requestStr
     */
    private void entrance(InterfaceLimit prevent, String requestStr,String methodFullName) throws Exception {
        PreventStrategy strategy = prevent.strategy();
        switch (strategy) {
            case DEFAULT:
                defaultHandle(requestStr, prevent,methodFullName);
                break;
            default:
                throw new BusinessSilentException("无效的策略");
        }
    }


    /**
     * 默认处理方式
     *
     * @param requestStr
     * @param prevent
     */
    private void defaultHandle(String requestStr, InterfaceLimit prevent, String methodFullName) throws Exception {
        String base64Str = toBase64String(requestStr);
        long expire = Long.parseLong(prevent.value());

        String resp = redisUtil.get(methodFullName+base64Str) == null ? null :
                redisUtil.get(methodFullName+base64Str).toString();
        if (StringUtils.isEmpty(resp)) {
            redisUtil.set(methodFullName+base64Str, requestStr, expire);
        } else {
            String message = !StringUtils.isEmpty(prevent.message()) ? prevent.message() :
                    expire + "秒内不允许重复请求";
            throw new BusinessSilentException(message);
        }
    }


    /**
     * 对象转换为base64字符串
     *
     * @param obj 对象值
     * @return base64字符串
     */
    private String toBase64String(String obj) throws Exception {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = obj.getBytes("UTF-8");
        return encoder.encodeToString(bytes);
    }
}
