package com.abc.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import com.abc.annotation.APICallLimiter;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
public class APICallLimiterAspect {
    HashMap<String,Integer> hashMap=new HashMap<>();
    @Around("@annotation(APICallLimiter)")
    public Object around(ProceedingJoinPoint joinPoint, APICallLimiter APICallLimiter) throws Throwable {
        int limitTimes = APICallLimiter.limitTimes();
        String limitByParam = APICallLimiter.param();
        //获取参数名称和值
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args=joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        System.out.println("AOP生效了");
        //控制方法是否生效
        Boolean isWorking=false;
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if(paramNames[i].equals(limitByParam)){
                    String keySid=(String)args[i]+method.getName();
                    if(!hashMap.containsKey(keySid))
                        hashMap.put(keySid,0);
                    hashMap.put(keySid,hashMap.get(keySid)+1);
                    System.out.println("number:"+hashMap.get(keySid));
                    //每当某个学生调用该方法10次后，方法生效
                    if(hashMap.get(keySid)==limitTimes){
                        hashMap.put(keySid,0);
                        isWorking=true;
                        System.out.println("生效啦");
                    }
                }
                params = paramNames[i] + ": " + args[i];
                System.out.println(params);
            }
        }
        if(isWorking){
            Object o = joinPoint.proceed();
            return o;
        }
        return null;
    }
}
