package com.wql.boot.wqlboot.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

//import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wql.boot.wqlboot.domain.user.User;

/**
 * 
 * @author wangqiulin
 *
 */
@SuppressWarnings("resource")
public class POIUtil {

    public static void ExcelOperate(String filePath) throws Exception {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
        Sheet first = workbook.getSheetAt(0);
        for (int i = 0; i < 100000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if(i == 0) {
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    if (j == 0) {
                        row.createCell(j).setCellValue(i);
                    } else
                        row.createCell(j).setCellValue(Math.random());
                }
            }
        }
        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        workbook.write(out);
        out.close();
    }

    public static void Excel2007AboveOperateOld(String filePath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        // 获取第一个表单
        Sheet first = workbook.getSheetAt(0);
        for (int i = 0; i < 100000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if(i == 0) {
                    // 首行
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    // 数据
                    if (j == 0) {
                        CellUtil.createCell(row, j, String.valueOf(i));
                    } else
                        CellUtil.createCell(row, j, String.valueOf(Math.random()));
                }
            }
        }
        // 写入文件
        FileOutputStream out = new FileOutputStream("workbook.xlsx");
        workbook.write(out);
        out.close();
    }

    /**
     * 测试写入百万条数据
     * @param filePath 文件路径
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static void Excel2007AboveOperate(String filePath) throws IOException {
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
        Sheet first = sxssfWorkbook.getSheetAt(0);
        for (int i = 0; i < 10000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if(i == 0) {
                    // 首行
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    // 数据
                    if (j == 0) {
                        CellUtil.createCell(row, j, String.valueOf(i));
                    } else
                        CellUtil.createCell(row, j, String.valueOf(Math.random()));
                }
            }
        }
        FileOutputStream out = new FileOutputStream(filePath);
        sxssfWorkbook.write(out);
        out.close();
    }
    
    
    /**
     * 真实对象
     * 
     * @param filePath
     * @param headList
     * @param dataList
     * @throws IOException
     */
	public static void Excel2007AboveOperate(String filePath, List<String> headList, List<User> dataList) throws IOException {
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
        Sheet first = sxssfWorkbook.getSheetAt(0);
        //
        int headSize = headList.size();
        int totalSize = dataList.size();
        for (int i = 0; i < totalSize; i++) {
        	Row row = first.createRow(i);
            for (int j = 0; j < headSize; j++) {
                if(i == 0) {
                    // 首行
                    row.createCell(j).setCellValue(headList.get(j));
                } else {
                    // 数据
                	switch (j) {
						case 0:
							CellUtil.createCell(row, j, String.valueOf(i));
							break;
						case 1:
							CellUtil.createCell(row, j, String.valueOf(dataList.get(i).getId()));
							break;
						case 2:
							CellUtil.createCell(row, j, String.valueOf(dataList.get(i).getUserName()));
							break;
						case 3:
							CellUtil.createCell(row, j, String.valueOf(dataList.get(i).getPassword()));
							break;
						default:
							break;
					}
                }
            }
		}
        FileOutputStream out = new FileOutputStream(filePath);
        sxssfWorkbook.write(out);
        out.close();
    }
    

    public static void MathRandomCastTime() {
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            Math.random();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
    }
    
    
    public static String toUtf8String(String s){ 
        StringBuffer sb = new StringBuffer(); 
          for (int i=0;i<s.length();i++){ 
             char c = s.charAt(i); 
             if (c >= 0 && c <= 255){sb.append(c);} 
           else{ 
           byte[] b; 
            try { b = Character.toString(c).getBytes("utf-8");} 
            catch (Exception ex) { 
                System.out.println(ex); 
                     b = new byte[0]; 
            } 
               for (int j = 0; j < b.length; j++) { 
                int k = b[j]; 
                 if (k < 0) k += 256; 
                 sb.append("%" + Integer.toHexString(k).toUpperCase()); 
                 } 
        } 
     } 
     return sb.toString(); 
    }
    
    
}
