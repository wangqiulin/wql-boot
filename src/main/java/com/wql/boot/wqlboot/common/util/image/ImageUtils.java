package com.wql.boot.wqlboot.common.util.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * 利用Base64编码和解码图片文件
 * 
 * @author wangqiulin
 *
 */
public class ImageUtils {

	/**
	 * 将网络图片进行Base64位编码
	 *
	 * @param imageUrl
	 *            图片url路径
	 * @return Base64编码后的字符串
	 * @throws IOException
	 */
	public static String encodeImgageToBase64(URL imageUrl) throws IOException {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageUrl);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			outputStream.close();
		}
		// 对字节数组Base64编码，返回Base64编码过的字节数组字符串
		return Base64.encodeBase64String(outputStream.toByteArray());
	}

	/**
	 * 将本地图片进行Base64位编码
	 *
	 * @author Michael Di
	 * @param imageFile
	 *            图片的url路径，如http://.....xx.jpg
	 * @return Base64编码后的字符串
	 * @throws IOException
	 */
	public static String encodeImgageToBase64(File imageFile) throws IOException {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageFile);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			outputStream.close();
		}
		// 对字节数组Base64编码，返回Base64编码过的字节数组字符串
		return Base64.encodeBase64String(outputStream.toByteArray());
	}
}
