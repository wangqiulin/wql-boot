package com.wql.boot.wqlboot.common.util.email;
/**
 * 邮箱格式校验
 * 
 * @author wangqiulin
 * @date 2018年3月21日
 */
public class EmailValidUtil {

	/**
	 * 检测是否为邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		String regEx = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		return email.matches(regEx);
	}
	
}
