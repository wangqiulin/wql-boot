package com.wql.boot.wqlboot.common.util.mobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号格式校验
 * 
 * @author wangqiulin
 * @date 2018年3月21日
 */
public class MobileValidUtil {
	
	private static final String REGULAR_EXPRESSION = "(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}";
	
	/**
	 * 检测手机号格式是否正确
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone){
		Pattern pattern = Pattern.compile(REGULAR_EXPRESSION);
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}
	
}
