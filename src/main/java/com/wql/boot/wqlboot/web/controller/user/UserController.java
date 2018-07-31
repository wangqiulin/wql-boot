package com.wql.boot.wqlboot.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wql.boot.wqlboot.common.constant.DataResponse;
import com.wql.boot.wqlboot.model.req.user.UserLoginReq;
import com.wql.boot.wqlboot.model.req.user.UserRegisterReq;
import com.wql.boot.wqlboot.model.req.user.UserUpdateReq;
import com.wql.boot.wqlboot.service.user.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"用户接口"})
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户注册")
	@PostMapping("/user/register")
	public DataResponse register(@RequestBody UserRegisterReq req) {
		return userService.register(req);
	}
	
	@ApiOperation(value="用户登录")
	@PostMapping("/user/login")
	public DataResponse login(@RequestBody UserLoginReq req) {
		return userService.login(req);
	}
	
	@ApiOperation(value="查询用户")
	@PostMapping("/user/queryUser")
	public DataResponse queryUser(@RequestBody String userName) {
		return userService.queryUser(userName);
	}
	
	@ApiOperation(value="查询用户列表")
	@PostMapping("/user/queryAll")
	public DataResponse queryAll() {
		return userService.queryAll();
	}
	
	@ApiOperation(value="修改用户信息")
	@PostMapping("/user/updateUser")
	public DataResponse updateUser(@RequestBody UserUpdateReq req) {
		return userService.updateUser(req);
	}
	
	@ApiOperation(value="删除用户")
	@PostMapping("/user/deleteUser")
	public DataResponse deleteUser(@RequestBody Integer dataId) {
		return userService.deleteUser(dataId);
	}
	
	
}
