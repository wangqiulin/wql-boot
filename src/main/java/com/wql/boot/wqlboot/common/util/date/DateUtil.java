package com.wql.boot.wqlboot.common.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
     * 获取第二天凌晨0点毫秒数
     */
    public static Date nextDayFirstDate() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天最后23：59:59点毫秒数
     */
    public static Date currentDayLastDate() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    
    
    /**
     * 获取 当天位于一年中的第几天
     * @return
     */
    public static Integer getDayOfYear(){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	Integer dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
    	System.out.println(dayOfYear);
    	return dayOfYear;
    }
    
    
    /**
     * 获取当天是哪一年
     * @return
     */
    public static Integer getCurrentYear(){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	Integer year = cal.get(Calendar.YEAR);
    	return year;
    }
    
    
    /**
     * 获取当天的日期：yyyy-MM-dd
     */
    public static String getCurrentDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        String date = sdf.format(new Date());  
        return date;
    }
    
    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串类型时间转Date类型
     */
    public static Date transString2Date(String dataStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        Date date = sdf.parse(dataStr);  
        return date;
    }
    
    
    /**
     * 日期之间相差的数
     * @param date1
     * @param date2
     * @return
     */
    public static long between(Date date1, Date date2) {
        return Math.abs(date2.getTime() - date1.getTime());
    }
    
    
    /**
     * 将Date类型转成yyyy-MM-dd HH:mm:ss格式的字符串类型
     * @return
     * @throws Exception
     */
    public static String tranDateToStr(Date date) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);  
    }
	
	
}
