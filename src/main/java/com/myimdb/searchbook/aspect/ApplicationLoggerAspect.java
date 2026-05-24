package com.myimdb.searchbook.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ApplicationLoggerAspect {

    @Around("execution(* com.myimdb.searchbook.controller..*(..)) || execution(* com.myimdb.searchbook.service.impl..*(..))")
    public Object logApplicationFlow(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long totalTime = System.currentTimeMillis() - startTime;
            log.info("Executed {} in {} ms", joinPoint.getSignature().toShortString(), totalTime);
            return result;
        } catch (Throwable throwable) {
            log.error("Failure in {} with message {}", joinPoint.getSignature().toShortString(), throwable.getMessage());
            throw throwable;
        }
    }
}
