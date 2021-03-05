package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RuntimeAop {
    /**
     * 仅做AOP练习
     * @param joinPoint
     * @return
     */

   // @Around("execution(* com.jt.service..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint){
        Object obj=null;
        try {
            long start = System.currentTimeMillis();
            obj = joinPoint.proceed();
            long runTime = System.currentTimeMillis()-start;
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            System.out.println("执行包路径："+className);
            System.out.println("执行方法："+methodName);
            System.out.println("耗时："+runTime);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }
        return obj;
    }

}
