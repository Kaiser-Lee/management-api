package com.iwhalecloud.retail.oms.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.iwhalecloud.retail.oms.dto.SelectBaseRequest;
import com.iwhalecloud.retail.oms.handler.OrderMangerHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLogManagerAop {

    @Autowired
    private OrderMangerHandler orderMangerHandler;
    static{
        /**
         * jdk1.8下若接口的缺省方法未被重写，则fastjson反系列化可能报错：java.lang.IncompatibleClassChangeError: Found interface xxx, but class was expected
         * 原因：实现类中未实现xxx interface的default方法，导致asm中设值的method属于接口(本应属于实现类)，调用错误。
         * 解决方法：禁止fastjson使用asm反系列化。
         * by ji.kai
         */
        ParserConfig.getGlobalInstance().setAsmEnable(false);
        SerializeConfig.getGlobalInstance().setAsmEnable(false);
    }
    @Around("execution(* com.iwhalecloud.retail.oms.service.*.*(..))")
    public Object aroundExecuteService(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();
        log.info("interface=({}),gs_start={},url={},request{}",
                JSON.toJSONString(point.getSignature().getDeclaringType()), time, point.getSignature().getName(),
                JSON.toJSONString((point.getArgs())));

        //开发阶段，模拟登录
//        development(point.getArgs());
        Object result = point.proceed();

        log.info("interface=({}),gs_close={},timeConsuming={},request{},result={}",
                JSON.toJSONString(point.getSignature().getDeclaringType()),time, (System.currentTimeMillis() - time),
                JSON.toJSONString(point.getArgs()), JSON.toJSONString(result));
        return result;


    }

    public void development(Object[] point) {
        SelectBaseRequest selectBaseRequest = new SelectBaseRequest();
        BeanUtils.copyProperties(point[0], selectBaseRequest);
        orderMangerHandler.login(selectBaseRequest.getMemberId(), selectBaseRequest.getUserSessionId());
    }

}
