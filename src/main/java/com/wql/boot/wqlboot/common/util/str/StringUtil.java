package com.wql.boot.wqlboot.common.util.str;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
 
/**
 * 关于字符串的一些实用操作
 * @author wangqiulin
 *
 */
public class StringUtil {
 
	/**
	 * 判断是否为整数
	 *
	 * @param str 传入的字符串
	 * @return 是整数返回true, 否则返回false boolean
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}
 
	/**
	 * 判断是否为浮点数，包括double和float
	 *
	 * @param str 传入的字符串
	 * @return 是浮点数返回true, 否则返回false boolean
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}
 
	/**
	 * 判断是不是合法字符 c 要判断的字符
	 *
	 * @param str the str
	 * @return the boolean
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}
 
	/**
	 * 从指定的字符串中提取Email content 指定的字符串
	 *
	 * @param content the content
	 * @return string
	 */
	public static String parse(String content) {
		String email = null;
		if (content == null || content.length() < 1) {
			return email;
		}
		// 找出含有@
		int beginPos;
		int i;
		String token = "@";
		String preHalf = "";
		String sufHalf = "";
 
		beginPos = content.indexOf(token);
		if (beginPos > -1) {
			// 前项扫描
			String s = null;
			i = beginPos;
			while (i > 0) {
				s = content.substring(i - 1, i);
				if (isLetter(s))
					preHalf = s + preHalf;
				else
					break;
				i--;
			}
			// 后项扫描
			i = beginPos + 1;
			while (i < content.length()) {
				s = content.substring(i, i + 1);
				if (isLetter(s))
					sufHalf = sufHalf + s;
				else
					break;
				i++;
			}
			// 判断合法性
			email = preHalf + "@" + sufHalf;
			if (isEmail(email)) {
				return email;
			}
		}
		return null;
	}
 
	/**
	 * 判断输入的字符串是否符合Email样式.
	 *
	 * @param email the email
	 * @return 是Email样式返回true, 否则返回false boolean
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}
 
	/**
	 * 判断输入的字符串是否为纯汉字
	 *
	 * @param str 传入的字符串
	 * @return 如果是纯汉字返回true, 否则返回false boolean
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}
 
	/**
	 * 判断是否为质数
	 *
	 * @param x the x
	 * @return boolean
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}
 
	/**
	 * 人民币转成大写
	 *
	 * @param str 数字字符串
	 * @return String 人民币转换成大写后的字符串
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
 
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分
 
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}
 
		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
 
	/**
	 * 去掉字符串中重复的子字符串
	 * 
	 * @param str
	 *            原字符串，如果有子字符串则用空格隔开以表示子字符串
	 * @return String 返回去掉重复子字符串后的字符串
	 */
	@SuppressWarnings("unused")
	private static String removeSameString(String str) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
		String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		return sb.toString();
	}
 
	/**
	 * 过滤特殊字符
	 *
	 * @param src the src
	 * @return string string
	 */
	public static String encoding(String src) {
		if (src == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		src = src.trim();
		for (int pos = 0; pos < src.length(); pos++) {
			switch (src.charAt(pos)) {
			case '\"':
				result.append("\"");
				break;
			case '<':
				result.append("<");
				break;
			case '>':
				result.append(">");
				break;
			case '\'':
				result.append("'");
				break;
			case '&':
				result.append("&");
				break;
			case '%':
				result.append("&pc;");
				break;
			case '_':
				result.append("&ul;");
				break;
			case '#':
				result.append("&shap;");
				break;
			case '?':
				result.append("&ques;");
				break;
			default:
				result.append(src.charAt(pos));
				break;
			}
		}
		return result.toString();
	}
	
	/**
	 * 判断是不是合法的手机号码
	 *
	 * @param handset the handset
	 * @return boolean boolean
	 */
	public static boolean isHandset(String handset) {
		try {
			String regex = "^1[\\d]{10}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(handset);
			return matcher.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}
	
	
	/**
	 * 字符串掩码
	 * @param str
	 * @param frontLength： 前面明文个数
	 * @param behindLength： 后面明文个数
	 * @param starLength： 星星的个数
	 * @return
	 */
	public static String maskStr(String str, int frontLength, int behindLength, int starLength) {
		if(StringUtils.isNotBlank(str)) {
			int length = str.length();
			int maskLength = frontLength + behindLength;
			String stars = getStars(starLength);
			return str.replaceAll("(\\d{"+frontLength+"})\\d{" + (length - maskLength) + "}(\\d{"+behindLength+"})", "$1"+stars+"$2");
		}
		return null;
	}
	
	public static String getStars(int length) {
		String START_1 = "*";
		StringBuffer blanks = new StringBuffer();
		for (int i = 0; i < length; i++) {
		    blanks.append(START_1);
		}
		return blanks.toString();
	}
	
}
