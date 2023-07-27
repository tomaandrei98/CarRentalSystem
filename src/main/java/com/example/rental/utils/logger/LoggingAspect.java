package com.example.rental.utils.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(Log)")
    public void logPointcut() {}

    @Before("logPointcut()")
    public void logAllMethodBeforeCallsAdvice(JoinPoint joinPoint) {
        log.info("enter class: " + joinPoint.getSignature().getDeclaringTypeName() +
                ", method: " + joinPoint.getSignature().getName() +
                ", args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("logPointcut()")
    public void logAllMethodAfterCallsAdvice(JoinPoint joinPoint) {
        log.info("exit class: " + joinPoint.getSignature().getDeclaringTypeName() +
                ", method: " + joinPoint.getSignature().getName() +
                ", args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing("logPointcut()")
    public void afterThrowingAdvice(JoinPoint joinPoint) {
        log.warn("warn in class: " + joinPoint.getSignature().getDeclaringTypeName() +
                ", method: " + joinPoint.getSignature().getName() +
                ", args: " + Arrays.toString(joinPoint.getArgs()));
    }
}
