package com.wql.boot.wqlboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class WqlBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WqlBootApplication.class, args);
	}
	
	/**
	 * 将应用打车war包时， 启动类需要继承SpringBootServletInitializer， 然后重写configure()
	 */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WqlBootApplication.class);
    }
	
}
