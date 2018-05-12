package com.wql.boot.wqlboot.config.redisson;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Configuration
public class RedissonClientConfig {

//	@Autowired
//	private Environment env;
	
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() throws IOException {
		/*String[] profiles = env.getActiveProfiles();
		String profile = "";
		if(profiles.length > 0) {
			profile = "-" + profiles[0];
		}*/
		return Redisson.create(
			//Config.fromYAML(new ClassPathResource("config/redisson" + profile + ".yml").getInputStream())
			Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream())
		);
	}
	
}
