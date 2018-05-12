package com.wql.boot.wqlboot.service.user;

import com.wql.boot.wqlboot.domain.user.User;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
public interface UserService {

	void register(User user);

	void login(String name, String password);

	User queryByName(String name);
	
	void updateByName(String name, String password);
	
	void deleteByName(String name);
	
}
