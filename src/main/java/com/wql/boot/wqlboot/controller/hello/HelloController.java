package com.wql.boot.wqlboot.controller.hello;
/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wql.boot.wqlboot.common.properties.SystemParamProperty;
import com.wql.boot.wqlboot.common.properties.WqlBootProperty;
import com.wql.boot.wqlboot.service.mail.MailService;
import com.wql.boot.wqlboot.service.rabbitmq.sender.HelloSender;

import freemarker.template.TemplateException;

@RestController
public class HelloController {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
    private MailService mailService;
	
//	@Autowired
//	private TemplateEngine templateEngine;
	 
	@Autowired
	private HelloSender helloSender;
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Autowired
	private SystemParamProperty systemParamProperty;
	
	@Autowired
	private WqlBootProperty wqlBootProperty;
	
	
	@RequestMapping("/hello")
	public String index() {
		//测试redis
		redisTemplate.opsForValue().set("wqlboot:test", "wql");
        return "Hello World !!!";
	}
	
	
	@RequestMapping("/uid")
	public String uid(HttpSession session) {
		//测试spring-session共享
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
	
	
	@RequestMapping("/mail")
	public String mail() throws TemplateException, IOException {
		mailService.sendSimpleMail("xxx@qq.com","test simple mail"," hello this is simple mail");
		//thymeleaf邮件模板
        /*Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail("xxx@qq.com","主题：这是模板邮件",emailContent);*/
        
        //freemarker模板
        /*Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "/WEB-INF/freemarker/");
        Template emailTemplate = cfg.getTemplate("emailTemplate2.html");
        StringWriter out = new StringWriter();
        emailTemplate.process(null, out);*/
        return "Hello mail !!!";
	}
	
	
	
	@RequestMapping("/rabbitmq")
	public String rabbitmq() {
		helloSender.send();
        return "Hello rabbitmq !!!";
	}
	
	
	@RequestMapping("/redissonClient")
	public String redissonClient() {
		RLock lock = redissonClient.getLock("TEST");
        try {
            lock.lock();
            String str = "========8081=========="+redisTemplate.opsForValue().get("COUNTER");
            System.out.println(str);
            return str;
        } catch (Exception ex) {
        	
        } finally {
            lock.unlock();
        }
        return "Hello redissonClient !!!";
	}
	
	
	@RequestMapping("/systemParamProperty")
	public String systemParam() {
		System.out.println("default.key02: "+systemParamProperty.paramKey02);
        return "systemParamProperty";
	}
	
	@RequestMapping("/wqlBootProperty")
	public String wqlBootProperty() {
		System.out.println("wql-boot.test.name: "+wqlBootProperty.getName());
        return "wqlBootProperty";
	}
	
	
}
