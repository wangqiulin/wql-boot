package com.wql.boot.wqlboot.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.poi.hssf.util.HSSFColor;

import com.xuxueli.poi.excel.annotation.ExcelField;
import com.xuxueli.poi.excel.annotation.ExcelSheet;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@ExcelSheet(name = "用户列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
@Table(name="t_user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id") 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ExcelField(name = "姓名")
	private String userName;
	
	@ExcelField(name = "密码")
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
