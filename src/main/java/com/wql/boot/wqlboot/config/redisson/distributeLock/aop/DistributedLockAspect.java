package com.wql.boot.wqlboot.config.redisson.distributeLock.aop;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.wql.boot.wqlboot.config.redisson.distributeLock.DistributedLockCallback;
import com.wql.boot.wqlboot.config.redisson.distributeLock.DistributedLockTemplate;

@SuppressWarnings("all")
@Aspect
@Order(-99)  // 保证该AOP在@Transactional之前执行
@Component
public class DistributedLockAspect {

    @Autowired
    private DistributedLockTemplate lockTemplate;

    @Pointcut("@annotation(com.wql.boot.wqlboot.config.redisson.distributeLock.aop.DistributedLock)")
    public void DistributedLockAspect() {}
    
	@Around(value = "DistributedLockAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //切点所在的类
        Class targetClass = pjp.getTarget().getClass();
        //使用了注解的方法
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);
        Object[] arguments = pjp.getArgs();
        final String lockName = getLockName(method, arguments);
        return lock(pjp, method, lockName);
    }

    @AfterThrowing(value = "DistributedLockAspect()", throwing="ex")
    public void afterThrowing(Throwable ex) {
        throw new RuntimeException(ex);
    }

    public String getLockName(Method method, Object[] args) {
        Objects.requireNonNull(method);
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        String lockName = annotation.lockName(),
        param = annotation.param();
        //如果设置了lockName,则使用此锁名称； 否则通过param和argNum获取锁名称
        if (StringUtils.isBlank(lockName)) {
            if (args.length > 0) {
                if (StringUtils.isNotBlank(param)) {
                    Object arg;
                    if (annotation.argNum() > 0) {
                        arg = args[annotation.argNum() - 1];
                    } else {
                        arg = args[0];
                    }
                    lockName = String.valueOf(getParam(arg, param));
                } else if (annotation.argNum() > 0) {
                    lockName = args[annotation.argNum() - 1].toString();
                }
            }
        } 
        if (StringUtils.isNotBlank(lockName)) {
        	//锁名称不为空
        	String preLockName = annotation.lockNamePre(),
        	postLockName = annotation.lockNamePost(),
        	separator = annotation.separator();
            StringBuilder lName = new StringBuilder();
            if (StringUtils.isNotBlank(preLockName)) {
                lName.append(preLockName).append(separator);
            }
            lName.append(lockName);
            if (StringUtils.isNotBlank(postLockName)) {
                lName.append(separator).append(postLockName);
            }
            lockName = lName.toString();
            return lockName;
        }
        throw new IllegalArgumentException("Can't get or generate lockName accurately!");
    }

    /**
     * 从方法参数获取数据
     *
     * @param param
     * @param arg 方法的参数数组
     * @return
     */
    public Object getParam(Object arg, String param) {
        if (StringUtils.isNotBlank(param) && arg != null) {
            try {
                Object result = PropertyUtils.getProperty(arg, param);
                return result;
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + "没有属性" + param + "或未实现get方法。", e);
            } catch (Exception e) {
                throw new RuntimeException("", e);
            }
        }
        return null;
    }

    public Object lock(ProceedingJoinPoint pjp, Method method, final String lockName) {
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        boolean fairLock = annotation.fairLock();
        boolean tryLock = annotation.tryLock();
        if (tryLock) {
            return tryLock(pjp, annotation, lockName, fairLock);
        } else {
            return lock(pjp,lockName, fairLock);
        }
    }

    public Object lock(ProceedingJoinPoint pjp, final String lockName, boolean fairLock) {
        return lockTemplate.lock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, fairLock);
    }

    public Object tryLock(ProceedingJoinPoint pjp, DistributedLock annotation, final String lockName, boolean fairLock) {
        long waitTime = annotation.waitTime(), leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();
        return lockTemplate.tryLock(new DistributedLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, waitTime, leaseTime, timeUnit, fairLock);
    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

}
