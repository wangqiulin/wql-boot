package com.wql.boot.wqlboot.model.domain.user;

import javax.persistence.Table;

import com.wql.boot.wqlboot.common.base.BaseDomain;

@Table(name="t_user")
public class User extends BaseDomain {

	private String userName;
	
	private String userPwd;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}


}
