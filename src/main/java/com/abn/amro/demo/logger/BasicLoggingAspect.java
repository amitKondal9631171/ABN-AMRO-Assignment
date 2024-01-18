package com.abn.amro.demo.logger;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
 import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class BasicLoggingAspect {

    @Before("execution(* com.abn.amro.demo.*.*.*(..))")
    public void before(JoinPoint joinPoint ){
        String className = joinPoint.getSignature().getDeclaringType().getCanonicalName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info( className + " ======= " + signature.getMethod().getName() );
    }

    @After("execution(* com.abn.amro.demo.*.*.*(..))")
    public void after(JoinPoint joinPoint){
        Object[] signatureArgs = joinPoint.getArgs();
        for (Object signatureArg: signatureArgs) {
            log.info("Arg: " + signatureArg);
        }
    }
}
