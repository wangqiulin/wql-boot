package com.wql.boot.wqlboot.common.util.excel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * 转换excel文件时，日期类型，长数字类型等的显示
 * @author wangqiulin
 *
 */
public class TransCellTypeUtil {

	@SuppressWarnings("all")
	public static String getCellFormatValue(XSSFCell cell) {
        String cellvalue = null;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()){
               // 如果当前Cell的Type为NUMERIC
               case HSSFCell.CELL_TYPE_NUMERIC: 
               case HSSFCell.CELL_TYPE_FORMULA: {
                  // 判断当前的cell是否为Date
                  if (HSSFDateUtil.isCellDateFormatted(cell)){
                     // 如果是Date类型则，取得该Cell的Date值
                     Date date = cell.getDateCellValue();
                     // 把Date转换成本地格式的字符串
                     cellvalue = cell.getDateCellValue().toLocaleString();
                  } else{   // 如果是纯数字
                     // 取得当前Cell的数值
                     double num = new Double((double)cell.getNumericCellValue());
                     cellvalue = double2DateStr(num);
                  }
                  break;
               }
               // 如果当前Cell的Type为STRIN
               case HSSFCell.CELL_TYPE_STRING:
                  // 取得当前的Cell字符串
                  cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                  break;
            }
        }
        return cellvalue;
    }
	
	
	public static String double2DateStr(Double d){
	    try{   
           Calendar base = Calendar.getInstance();   
           SimpleDateFormat outFormat = new SimpleDateFormat("yyyy年MM月");   
           //Delphi的日期类型从1899-12-30开始
           base.set(1899, 11, 30, 0, 0, 0);
           base.add(Calendar.DATE, d.intValue());   
           base.add(Calendar.MILLISECOND,(int)((d % 1) * 24 * 60 * 60 * 1000));   
           return outFormat.format(base.getTime());   
	    }   
	    catch(Exception e)   {   
	        e.printStackTrace();      
	    }
	    return null;
	}
	
	
}
