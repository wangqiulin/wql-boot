package com.wql.boot.wqlboot.config.rabbitmq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  
  
/**
 * 消息生产者  
 * 
 * @author wangqiulin
 */
@Component  
public class Send implements RabbitTemplate.ConfirmCallback {  
  
	private static final Logger logger = LoggerFactory.getLogger(Send.class);
	
    private RabbitTemplate rabbitTemplate;  
  
    @Autowired  
    public Send(RabbitTemplate rabbitTemplate) {  
        this.rabbitTemplate = rabbitTemplate;  
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容  
    }  
  
    /**
     * 消息发送方法
     */
    public void sendMsg(String content) {  
    	String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        CorrelationData correlationId = new CorrelationData(date + "-" + uuid);  
        rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, content, correlationId);  
        logger.info("消息已发出---> id:{} ", correlationId.getId());
    }  
  
    /**  
     * 回调  
     */  
    @Override  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
    	logger.info("【回调】id:" + correlationData.getId());
        if (ack) {  
        	logger.info("消息成功消费!!!");
        } else {  
        	logger.info("消息消费失败:{}", cause);
        }  
    }  
  
}