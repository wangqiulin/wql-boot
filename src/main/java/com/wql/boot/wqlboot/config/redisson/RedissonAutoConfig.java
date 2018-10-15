package com.wql.boot.wqlboot.config.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wql.boot.wqlboot.common.support.lock.DistributedLocker;
import com.wql.boot.wqlboot.common.support.lock.RedissonLockUtil;
import com.wql.boot.wqlboot.common.support.lock.impl.RedissonDistributedLocker;

@Configuration
@ConditionalOnClass(Config.class)
//@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfig {

	@Autowired
	private RedissonProperties redssionProperties;

	/**
	 * 单机模式自动装配
	 */
	@Bean
	@ConditionalOnProperty(name = "redisson.address")
	RedissonClient redissonSingle() {
		Config config = new Config();
		SingleServerConfig serverConfig = config.useSingleServer()
				.setAddress(redssionProperties.getAddress())
				.setTimeout(redssionProperties.getTimeout())
				.setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
				.setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
		//在redis进行连接的时候如果不对密码进行空判断，会出现AUTH校验失败的情况。	
		if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
			serverConfig.setPassword(redssionProperties.getPassword());
		}
		return Redisson.create(config);
	}
	
	
	/**
	 * 哨兵模式自动装配
	 */
	@Bean
	@ConditionalOnProperty(name = "redisson.master-name")
	RedissonClient redissonSentinel() {
		Config config = new Config();
		SentinelServersConfig serverConfig = config.useSentinelServers()
				.addSentinelAddress(redssionProperties.getSentinelAddresses())
				.setMasterName(redssionProperties.getMasterName())
				.setTimeout(redssionProperties.getTimeout())
				.setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
				.setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
		//在redis进行连接的时候如果不对密码进行空判断，会出现AUTH校验失败的情况。	
		if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
			serverConfig.setPassword(redssionProperties.getPassword());
		}
		return Redisson.create(config);
	}
	

	/**
	 * 装配locker类，并将实例注入到RedissLockUtil中
	 */
	@Bean
	DistributedLocker distributedLocker(RedissonClient redissonClient) {
		DistributedLocker locker = new RedissonDistributedLocker();
		((RedissonDistributedLocker) locker).setRedissonClient(redissonClient);
		RedissonLockUtil.setLocker(locker);
		return locker;
	}

}
