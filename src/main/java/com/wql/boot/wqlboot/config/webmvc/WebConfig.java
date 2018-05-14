package com.wql.boot.wqlboot.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 如果不是static路径，而是自定义了静态资源的路径时，需要添加该文件配置
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
        registry.addResourceHandler("/WEB-INF/static/**").addResourceLocations("classpath:/WEB-INF/static/");
        super.addResourceHandlers(registry);
    }
    
}
