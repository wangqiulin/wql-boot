package com.wql.boot.wqlboot.common.util.number;

import java.text.DecimalFormat;

/**
 * 数据格式化
 * @author wangqiulin
 *
 */
public class NumberUtil extends org.apache.commons.lang3.math.NumberUtils {

	 /**
     * 格式化number 默认去掉尾数0
     *
     * @param number
     * @return
     */
    public static String formatNumber(Number number) {
        return formatNumber(number,"#,###.##");
    }

    /**
     * 格式化number,自定义pattern
     * <a href="https://blog.csdn.net/thunder4393/article/details/1739911">pattern定义</>
     *
     * @param number
     * @param pattern
     * @return
     */
    public static String formatNumber(Number number, String pattern) {
        if(number==null){
            return null;
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);
    }
	
	
}
