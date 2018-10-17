package com.wql.boot.wqlboot.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

/**
 * RabbitMQ配置
 * 
 * @author wangqiulin
 * @date 2018年10月17日
 */
@Configuration
public class AmqpConfig {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private Integer port;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.virtual-host}")
	private String vHost;

	@Bean
	public ConnectionFactory connectionFactory() {
		// CachingConnectionFactory实例，其缓存模式为通道缓存
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(host);
		connectionFactory.setPort(port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(vHost);
		connectionFactory.setPublisherConfirms(true); // 必须要设置true, 才能进行消息的回调。
		return connectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) throws Exception {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		return rabbitAdmin;
	}

	// 如果需要在生产者需要消息发送后的回调，需要对rabbitTemplate设置ConfirmCallback对象，由于不同的生产者需要对应不同的ConfirmCallback，
	// 如果rabbitTemplate设置为单例bean，则所有的rabbitTemplate,
	// 实际的ConfirmCallback为最后一次申明的ConfirmCallback。
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 必须是prototype类型
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

	// ================================消费者=============================//

	public static final String EXCHANGE = "wql-boot-exchange";
	public static final String ROUTINGKEY = "wql-boot-routingKey";

	/**
	 * 针对消费者配置 FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 HeadersExchange
	 * ：通过添加属性key-value匹配 DirectExchange:按照routingkey分发到指定队列 TopicExchange:多关键字匹配
	 * 
	 * @return
	 */
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE); // 在申明交换机时需要指定交换机名称，默认创建可持久交换机
	}

	@Bean
	public Queue queue() {
		return new Queue("wql-boot-queue", true); // true:队列持久
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(defaultExchange()).with(ROUTINGKEY);
	}

	// 通过消息监听容器实现消息的监听，在消息到来时执行回调操作。
	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
		// 设置队列信息
		container.setQueues(queue());
		// 添加队列信息
		container.setExposeListenerChannel(true);
		// 设置并发消费者数量，默认情况为1
		container.setMaxConcurrentConsumers(100);
		container.setConcurrentConsumers(10);
		// 设置确认模式手工确认
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		// 监听的业务逻辑
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				byte[] body = message.getBody();
				String content = new String(body);
				System.err.println("receive msg : " + content);
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
			}
		});
		return container;
	}

}
