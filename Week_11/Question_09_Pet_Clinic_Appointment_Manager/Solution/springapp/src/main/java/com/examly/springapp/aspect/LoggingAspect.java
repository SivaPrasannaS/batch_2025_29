package com.examly.springapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.examly.springapp.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Pointcut("execution(* com.examly.springapp.service.impl.*.*(..))")
    public void serviceMethods() {
    }

    @Before("controllerMethods()")
    public void logControllerCall(JoinPoint joinPoint) {
        logger.info("Controller method called: {}", joinPoint.getSignature().getName());
    }

    @Before("serviceMethods()")
    public void logServiceCall(JoinPoint joinPoint) {
        logger.info("Service method called: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("serviceMethods()")
    public void logServiceSuccess(JoinPoint joinPoint) {
        logger.info("Service method {} returned successfully", joinPoint.getSignature().getName());
    }
}
