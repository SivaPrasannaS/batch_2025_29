package com.examly.springapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.examly.springapp.utils.MethodMetricsRegistry;

@Aspect
@Component
public class OrderLifecycleAspect {

    private static final Logger logger = LoggerFactory.getLogger(OrderLifecycleAspect.class);
    private static final String SERVICE_METHODS = "execution(* com.examly.springapp.service.impl.DeliveryOrderServiceImpl.*(..))";

    private final MethodMetricsRegistry registry;

    public OrderLifecycleAspect(MethodMetricsRegistry registry) {
        this.registry = registry;
    }

    @Before(SERVICE_METHODS)
    public void logStart(JoinPoint joinPoint) {
        logger.info("Starting {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(SERVICE_METHODS)
    public void logSuccess(JoinPoint joinPoint) {
        logger.info("Completed {} successfully", joinPoint.getSignature().getName());
    }

    @After(SERVICE_METHODS)
    public void logFinish(JoinPoint joinPoint) {
        logger.info("Finished {}", joinPoint.getSignature().getName());
    }

    @Around(SERVICE_METHODS)
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            registry.record(methodName, elapsed);
            logger.info("{} took {} ms", methodName, elapsed);
        }
    }
}
