package com.wql.boot.wqlboot.controller.test;

import io.swagger.annotations.Api;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wql.boot.wqlboot.common.property.ParamProperty;
import com.wql.boot.wqlboot.service.user.UserService;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Api(tags={"测试接口"})
@RestController
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private RedissonClient redissonClient;
	
	@Autowired
	private ParamProperty paramProperty;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/redisson")
	public String redissonClient() {
		RLock lock = redissonClient.getLock("test");
		try {
			boolean locked = lock.tryLock(5000, 5000, TimeUnit.MILLISECONDS);
            if(locked) {
            	logger.info("成功获取到锁");
            } 
            return locked+"";
		} catch (Exception e) {
        	logger.error("设置分布式锁异常：", e);
        } finally {
        	if(lock != null) {
        		lock.unlock();
        	}
        }
        return "exception";
	}
	
	@GetMapping("/xxlConf")
	public String systemParam() {
		//分布式xxl-conf
        return paramProperty.paramKey02;
	}
	
	@GetMapping("/export")
	public void export(){
	    userService.export();
	}
	
	
}
