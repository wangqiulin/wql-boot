package com.wql.boot.wqlboot.common.util.csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.ApiModelProperty;

/**
 * 使用说明：
 * 		Map<String, String> csvHeader = CsvDownloadUtil.getCSVHeader(QueryCashOrderRes.class);
        CsvDownloadUtil.writeHeader(csvHeader, sheetNamePrefix, response);
        
        List<QueryCashOrderRes> dataList = new ArrayList<>();  //将QueryCashOrderRes转成Map<String, Object>
        List<Map<String, Object>> list = new ArrayList<>();
        CsvDownloadUtil.writeData(csvHeader, list, response);
 * 
 * csv文件下载
 * 
 * @author wangqiulin
 */
public class CsvDownloadUtil {

	private static final Logger logger = LoggerFactory.getLogger(CsvDownloadUtil.class);

	private static String EMPTY_STR = "";

	/**
	 * 查询带 ApiModelProperty注解的字段
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
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
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> dmap : data) {
            for (String key : csvHeader.keySet()) {
                Object val = dmap.get(key);
                sb.append(cover2cscValue(val)+"\t").append(",");
            }
            sb.append("\n");
        }
		PrintWriter out = response.getWriter();
		out.print(sb.toString());
		out.flush();
	}

	@SuppressWarnings("all")
	public static void writeHeader(Map<String, String> csvHeader, String fileName, HttpServletResponse response)
			throws IOException {
		logger.info("开始生成csv文件->{}", fileName);
		response.reset();
		fileName = overrideFileName(fileName + ".csv");
		fileName = URLEncoder.encode(fileName, "UTF-8");
		// 跨域请求头
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Method", "*");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream;file-name=" + fileName);
		// 写入第一行header
		List header = Arrays.asList(csvHeader);
		writeData(csvHeader, header, response);
	}

	/**
	 * 重写fileName添加时间
	 */
	private static String overrideFileName(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return fileName;
		}
		String dateSuffix = "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if (fileName.contains(".")) {
			int index = fileName.lastIndexOf(".");
			return fileName.substring(0, index) + dateSuffix + fileName.substring(index, fileName.length());
		} else {
			return fileName + dateSuffix;
		}
	}

	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	private static String cover2cscValue(Object javaValue) {
		if (javaValue == null) {
			return EMPTY_STR;
		}
		String strVal;
		if (javaValue instanceof String) {
			strVal = javaValue.toString();
		} else if (javaValue instanceof Integer || javaValue instanceof Long) {
			strVal = javaValue.toString();
		} else if (javaValue instanceof BigDecimal) {
			strVal = ((BigDecimal) javaValue).toPlainString();
		} else {
			// 未知类型直接toString,可能存在bug
			strVal = javaValue.toString();
			logger.warn("csv转换未知类型字段{}->{}", javaValue.getClass(), strVal);
		}
		String returnVal = strVal;
		if (isNumeric(returnVal)) {
			return returnVal;
		} else {
			// 加空格避免日期格式化
			returnVal = " " + returnVal;
		}
		if (strVal.contains(",")) {
			returnVal = "\"" + returnVal + "\"";
		}
		if (strVal.contains("\"")) {
			returnVal = returnVal.replaceAll("\"", "\"\"");
		}
		return returnVal;
	}

}
