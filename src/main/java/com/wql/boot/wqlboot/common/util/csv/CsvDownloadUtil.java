package com.wql.boot.wqlboot.common.util.csv;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * csv文件下载
 * 
 * @author wangqiulin
 */
public class CsvDownloadUtil {

    public static Map<String, String> getCSVHeader(Class<?> clazz) throws Exception {
        Map<String, String> header = new LinkedHashMap<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        Class<ApiModelProperty> apiModelPropertyClass = ApiModelProperty.class;
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(apiModelPropertyClass)) {
                String value = declaredField.getAnnotation(apiModelPropertyClass).value();
                header.put(declaredField.getName(), value);
            }
        }
        return header;
    }

    public static void writeData(Map<String, String> csvHeader, List<Map<String, Object>> data, 
    		HttpServletResponse response) throws IOException {
        String empty = "";
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> dmap : data) {
        	for (Entry<String, String> entry : csvHeader.entrySet()) {
        		Object val = dmap.get(entry.getKey());
                if (val != null) {
                    if (StringUtils.isNotEmpty(val.toString()) && isNumeric(val.toString())) {
                        sb.append(String.format("=\"%s\"", val.toString()));
                    } else {
                        sb.append(val.toString());
                    }
                } else {
                    sb.append(empty);
                }
                sb.append(",");
			}
            sb.append("\n");
        }
        response.getWriter().print(sb.toString());
        response.getWriter().flush();
    }

    public static void writeHeader(Map<String, String> csvHeader, String fileName, HttpServletResponse response) throws IOException {
        String header = Arrays.toString(csvHeader.values().toArray()).replace("[", "").replace("]", "");
        response.reset();
        fileName = overrideFileName(fileName + ".csv");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //跨域请求头
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "*");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream;file-name=" + fileName);
        response.getWriter().println(header);
    }

    /**
     * 重写fileName添加时间
     * @return
     */
    private static String overrideFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }
        String dateSuffix = "(" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ")";
        if (fileName.contains(".")) {
            int index = fileName.lastIndexOf(".");
            return fileName.substring(0, index)
                    + dateSuffix +
                    fileName.substring(index, fileName.length());
        } else {
            return fileName + dateSuffix;
        }
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    
}
