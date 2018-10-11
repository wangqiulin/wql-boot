package com.wql.boot.wqlboot.common.support.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

/**
 * @auther wangqiulin
 * @date 2018/10/11
 */
public interface DistributedLocker {

	RLock lock(String lockKey);

	RLock lock(String lockKey, int timeout);

	RLock lock(String lockKey, TimeUnit unit, int timeout);

	boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

	void unlock(String lockKey);

	void unlock(RLock lock);

}
