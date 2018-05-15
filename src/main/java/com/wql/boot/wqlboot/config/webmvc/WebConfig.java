package com.wql.boot.wqlboot.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author wangqiulin
 * @date 2018年5月13日
 */	
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 静态资源路径处理
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
    
}
