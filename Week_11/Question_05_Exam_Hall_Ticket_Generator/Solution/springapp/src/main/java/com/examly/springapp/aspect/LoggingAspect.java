package com.examly.springapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterReturning(pointcut = "execution(* com.examly.springapp.service.impl.HallTicketServiceImpl.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned: {}", joinPoint.getSignature().getName(), result);
    }

    @After("execution(* com.examly.springapp.service.impl.HallTicketServiceImpl.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method {} completed", joinPoint.getSignature().getName());
    }
}
