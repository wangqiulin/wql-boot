package com.wql.boot.wqlboot.service.user;

import com.wql.boot.wqlboot.common.constant.DataResponse;
import com.wql.boot.wqlboot.model.req.user.UserLoginReq;
import com.wql.boot.wqlboot.model.req.user.UserRegisterReq;
import com.wql.boot.wqlboot.model.req.user.UserUpdateReq;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
public interface UserService {

	/**
	 * 用户注册
	 * @param req
	 */
	DataResponse register(UserRegisterReq req);

	/**
	 * 用户登录
	 * @param req
	 */
	DataResponse login(UserLoginReq req);

	/**
	 * 根据用户名， 查询用户
	 * @param userName
	 * @return
	 */
	DataResponse queryUser(String userName);

	/**
	 * 查询用户列表
	 * @return
	 */
	DataResponse queryAll();
	
	/**
	 * 修改用户信息
	 * @param req
	 * @return
	 */
	DataResponse updateUser(UserUpdateReq req);

	/**
	 * 删除用户信息
	 * @param dataId
	 * @return
	 */
	DataResponse deleteUser(Integer dataId);

	/**
	 * 导出excel
	 */
    void export(String filePath);

}
