package com.jt.aop;

import com.jt.annotation.CacheFind;
import com.jt.utils.ObjectMapperUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Component
@Aspect
public class RedisAop {

//    @Autowired
//    private Jedis jedis;

    @Autowired
    private JedisCluster jedisCluster;

    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind){
        long start = System.currentTimeMillis();
        String key = cacheFind.key();
        if(StringUtils.isEmpty(key)){
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object arg0 = joinPoint.getArgs()[0];//参数列表中的第0个
            key = className+"."+methodName+"::"+arg0;
        }
        String value = jedisCluster.get(key);
        Object obj=null;
        try {
            if(StringUtils.isEmpty(value)){
                obj = joinPoint.proceed();
                value = ObjectMapperUtil.toJSON(obj);
                if(cacheFind.seconds()>0){
                    jedisCluster.setex(key,cacheFind.seconds(),value);
                }else{
                    jedisCluster.set(key,value);
                }
                long time=System.currentTimeMillis()-start;
                System.out.println("查询数据库时间："+time);
            }else{
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Class<?> returnType = methodSignature.getReturnType();
                obj = ObjectMapperUtil.toObj(value, returnType);
                long time=System.currentTimeMillis()-start;
                System.out.println("查询缓存耗时："+time);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }
        return obj;
    }



    @Pointcut("execution(* com.jt.service..*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("className:"+className);
        System.out.println("method:"+methodName);
    }

}
