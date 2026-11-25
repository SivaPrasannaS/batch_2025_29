package com.examly.springapp.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.examly.springapp.service.impl.EquipmentServiceImpl.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before method: {} with arguments: {}", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }
}
