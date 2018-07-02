package com.wql.boot.wqlboot.controller.user;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wql.boot.wqlboot.domain.user.User;
import com.wql.boot.wqlboot.service.user.UserService;
import com.xuxueli.poi.excel.ExcelExportUtil;
import com.xuxueli.poi.excel.ExcelImportUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Api(tags={"用户接口"})
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="注册")
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public void register(String name, String password) {
		User user = new User();
		user.setUserName(name);
		user.setPassword(password);
		userService.register(user);
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void login(String name, String password) {
		userService.login(name, password);
	}
	
	
	@RequestMapping(value="/queryByName", method = RequestMethod.POST)
	public String queryByName(String name) {
		User user = userService.queryByName(name);
		return JSON.toJSONString(user);
	}
	
	
	@RequestMapping(value="/updateByName", method = RequestMethod.POST)
	public String updateByName(String name, String password) {
		userService.updateByName(name, password);
		return "success";
	}
	
	
	@RequestMapping(value="/deleteByName", method = RequestMethod.POST)
	public String deleteByName(String name) {
		userService.deleteByName(name);
		return "success";
	}
	
	@RequestMapping(value="/excelOut", method = RequestMethod.POST)
	public String excelOut(String name) {
		List<User> userList = userService.queryList();
		ExcelExportUtil.exportToFile("C:\\data\\user.xls", userList);
		return "excelOut";
	}
	
	
	@RequestMapping(value="/excelInput", method = RequestMethod.POST)
	public String excelInput(String name) {
		List<Object> list = ExcelImportUtil.importExcel("C:\\data\\user.xls", User.class);
		if(CollectionUtils.isNotEmpty(list)) {
			for (Object obj : list) {
				User user = (User)obj;
				System.out.println(user.getUserName() + "," +user.getPassword());
			}
		}
		return "excelInput";
	}
	
	
	
}
