package com.wql.boot.wqlboot.common.util.excel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

/**
 * 下载时中文名称乱码
 *
 * @author wangqiulin
 * @date 2018年8月1日
 */
@SuppressWarnings("restriction")
public class DownLoadUtils {

	public static String getAttachmentFileName(String header, String filename) {
        // 火狐 MSIE
        if (header.contains("Firefox")) {
            BASE64Encoder base64 = new BASE64Encoder();
            try {
                filename = base64.encode(filename.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            filename = "=?utf-8?B?" + filename + "?=";
        } else {
            try {
                filename = URLEncoder.encode(filename, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }
	
}
