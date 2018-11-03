package com.wql.boot.wqlboot.common.support.lock.impl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.wql.boot.wqlboot.common.support.lock.DistributedLocker;

/**
 * @auther wangqiulin
 * @date 2018/10/11
 */
public class RedissonDistributedLocker implements DistributedLocker{

    private RedissonClient redissonClient;

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }


}
