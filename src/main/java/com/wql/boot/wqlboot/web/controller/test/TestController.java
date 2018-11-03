package com.wql.boot.wqlboot.web.controller.test;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wql.boot.wqlboot.common.property.ParamProperty;
import com.wql.boot.wqlboot.config.rabbitmq.Send;
import com.wql.boot.wqlboot.service.user.UserService;

import io.swagger.annotations.Api;

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
	
	@Autowired
	private Send send;
	
	
	@GetMapping("/rabbit/send")
	public void sendMsg() {
        for (int i=1; i<=3; i++){
        	send.sendMsg("生产消息 : "+i);
        }
	}
	
	@GetMapping("/redisson")
	public String redissonClient() {
		RLock lock = redissonClient.getLock("test");
		try {
			boolean locked = lock.tryLock(2000, 5000, TimeUnit.MILLISECONDS);
            if(locked) {
            	logger.info("成功获取到锁");
            } 
            return locked+"";
		} catch (Exception e) {
        	logger.error("设置分布式锁异常：", e);
        } finally {
        	lock.unlock();
        }
        return "exception";
	}
	
	@GetMapping("/xxlConf")
	public String systemParam() {
        return paramProperty.paramKey02;
	}
	
	@GetMapping("/export")
	public void export(String filePath){
	    userService.export(filePath);
	}
	
	
}
