package com.wql.boot.wqlboot.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Table(name="t_user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id") 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String userName;
	
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
