package com.abn.amro.demo.logger;


import org.aspectj.lang.JoinPoint;
 import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
 import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class BasicLoggingAspect {

    @Before("execution(* com.abn.amro.demo.*.*.*(..))")
    public void before(JoinPoint joinPoint ){
        String className = joinPoint.getSignature().getDeclaringType().getCanonicalName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println( className + " ======= " + signature.getMethod().getName() );
    }

    @After("execution(* com.abn.amro.demo.*.*.*(..))")
    public void after(JoinPoint joinPoint){
        Object[] signatureArgs = joinPoint.getArgs();
        for (Object signatureArg: signatureArgs) {
            System.out.println("Arg: " + signatureArg);
        }
    }
}
