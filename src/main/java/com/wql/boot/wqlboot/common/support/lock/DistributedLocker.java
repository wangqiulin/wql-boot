package com.wql.boot.wqlboot.common.support.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

/**
 * @auther wangqiulin
 * @date 2018/10/11
 */
public interface DistributedLocker {

	boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

	void unlock(RLock lock);

}
