//package com.wql.boot.wqlboot.config.redis;
//
//import java.io.IOException;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.ClassPathResource;
//
///**
// * redisson分布式配置
// * 
// * @author wangqiulin
// * @date 2018年5月10日
// */
//@Configuration
//public class RedissonClientConfig {
//
//	@Autowired
//	private Environment env;
//	
//	@Bean(destroyMethod = "shutdown")
//	public RedissonClient redissonClient() throws IOException {
//		String[] profiles = env.getActiveProfiles();
//		String profile = "";
//		if(profiles.length > 0) {
//			profile = "-" + profiles[0];
//		}
//		return Redisson.create(
//			Config.fromYAML(new ClassPathResource("config/redisson" + profile + ".yml").getInputStream())
//		);
//	}
//	
//}
