package com.wql.boot.wqlboot.service.mail;
/**
 * 发送邮件服务
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
public interface MailService {
	
	void sendSimpleMail(String to, String subject, String content);

	/**
	 * 发送html格式邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	void sendHtmlMail(String to, String subject, String content);
	
	/**
	 * 发送带附件的邮件
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePat
	 */
	void sendAttachmentsMail(String to, String subject, String content, String filePat);
	
	/**
	 * 发送带静态资源的邮件,静态资源一般就是指图片
	 * @param to
	 * @param subject
	 * @param content
	 * @param rscPath
	 * @param rscId
	 */
	void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
	
}
