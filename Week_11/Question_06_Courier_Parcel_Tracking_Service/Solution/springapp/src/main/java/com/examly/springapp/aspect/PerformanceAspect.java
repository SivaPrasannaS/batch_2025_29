package com.examly.springapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.examly.springapp.utils.MethodMetricsRegistry;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    private final MethodMetricsRegistry registry;

    public PerformanceAspect(MethodMetricsRegistry registry) {
        this.registry = registry;
    }

    @Around("execution(* com.examly.springapp.service.impl.ParcelServiceImpl.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            registry.record(methodName, elapsed);
            logger.info("{} executed in {} ms", methodName, elapsed);
        }
    }
}
