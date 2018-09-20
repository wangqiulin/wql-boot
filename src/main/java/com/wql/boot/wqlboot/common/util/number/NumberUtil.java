package com.wql.boot.wqlboot.common.util.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

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


    /**
     * 去掉逗号：   比如1,000 ---> 1000
     * @param str
     * @return
     * @throws ParseException
     */
    public static String trim(String str) throws ParseException {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.##", dfs);
        Number num = df.parse(str);
        return num.toString();
    }


    /**
     * bg1 与 bg2 的大小比较
     *    返回1： bg1 > bg2
     *    返回0： bg1 = bg2
     *    返回-1： bg1 < bg2
     *
     * @param bg1
     * @param bg2
     * @return
     */
    public int compare(BigDecimal bg1, BigDecimal bg2){
        return bg1.compareTo(bg2);
    }

    /**
     * 是否等于0
     * @param bg
     * @return
     */
    public boolean equalZero(BigDecimal bg){
        return BigDecimal.ZERO.compareTo(bg) == 0;
    }

    /**
     * 是否大于等于0
     * @param bg
     * @return
     */
    public boolean greatEqualZero(BigDecimal bg){
        return BigDecimal.ZERO.compareTo(bg) <= 0;
    }
	
	
}
