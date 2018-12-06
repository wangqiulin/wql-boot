package com.wql.boot.wqlboot.web.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Send send;
	
	@GetMapping("/test2")
	public Object success() {
       return "success";
	}
	
	@GetMapping("/rabbit/send")
	public void sendMsg() {
        for (int i=1; i<=3; i++){
        	send.sendMsg("生产消息 : "+i);
        }
	}
	
	@GetMapping("/export")
	public void export(String filePath){
	    userService.export(filePath);
	}
	
	
	
}
