package com.wql.boot.wqlboot.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 系统中，自定义配置的读取方式
 *
 * @author wangqiulin
 * @date 2018年5月12日
 */
@Configuration
@PropertySource(value = "classpath:wql-boot.properties")
@ConfigurationProperties(prefix = "wql-boot.test")
public class WqlBootProperty {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
