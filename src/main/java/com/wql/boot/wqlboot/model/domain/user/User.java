package com.wql.boot.wqlboot.model.domain.user;

import java.io.Serializable;

import javax.persistence.Table;

import com.wql.boot.wqlboot.common.base.BaseDomain;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Table(name="t_user")
public class User extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;

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
