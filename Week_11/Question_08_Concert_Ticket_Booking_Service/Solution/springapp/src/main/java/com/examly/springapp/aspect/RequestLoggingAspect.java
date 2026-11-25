package com.examly.springapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);

    @Around("execution(* com.examly.springapp.controller.*.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().getDeclaringType().getSimpleName() + "."
                + joinPoint.getSignature().getName();
        logger.info("Request: {} started", name);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            logger.info("Request: {} completed in {} ms", name, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable ex) {
            logger.warn("Request: {} failed: {}", name, ex.getMessage());
            throw ex;
        }
    }
}
