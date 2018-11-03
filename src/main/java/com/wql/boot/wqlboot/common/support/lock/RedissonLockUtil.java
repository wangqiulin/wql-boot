package com.wql.boot.wqlboot.common.support.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁帮助类
 * 
 * @author wangqiulin
 *
 */
public class RedissonLockUtil {

	private static DistributedLocker redissLock;

	public static void setLocker(DistributedLocker locker) {
		redissLock = locker;
	}

	public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
		return redissLock.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
	}

	public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
		return redissLock.tryLock(lockKey, unit, waitTime, leaseTime);
	}
	
	public static void unlock(RLock lock) {
		redissLock.unlock(lock);
	}
	
}
