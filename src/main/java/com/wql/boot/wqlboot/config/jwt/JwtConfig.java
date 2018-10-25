package com.wql.boot.wqlboot.config.jwt;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.wql.boot.wqlboot.web.filter.JwtFilter;

/**
 * 注册jwt认证过滤器
 * 
 * @author wangqiulin
 *
 */
@Configuration
public class JwtConfig {

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		
		// 添加需要拦截的url
		List<String> urlPatterns = Lists.newArrayList();
		urlPatterns.add("/*");
		registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
		return registrationBean;
	}
	
}