package com.wql.boot.wqlboot.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wql.boot.wqlboot.domain.user.User;
import com.wql.boot.wqlboot.service.user.UserService;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	public void register(String name, String password) {
		User user = new User();
		user.setUserName(name);
		user.setPassword(password);
		userService.register(user);
	}
	
	
	@RequestMapping("/login")
	public void login(String name, String password) {
		userService.login(name, password);
	}
	
	
	@RequestMapping("/queryByName")
	public String queryByName(String name) {
		User user = userService.queryByName(name);
		return JSON.toJSONString(user);
	}
	
	
	@RequestMapping("/updateByName")
	public String updateByName(String name, String password) {
		userService.updateByName(name, password);
		return "success";
	}
	
	
	@RequestMapping("/deleteByName")
	public String deleteByName(String name) {
		userService.deleteByName(name);
		return "success";
	}
	
	
}
