package com.wql.boot.wqlboot.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author wangqiulin
 * @date 2018年5月12日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${wql-boot.config.controller.location}")
	private String controllerLocation;
	
	@Value("${wql-boot.config.swagger.enabled : true}") 
	private Boolean enabled;
	
	@Lazy
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enabled) //是否开启swagger
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllerLocation))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("wql-boot restful apis")
                .description("wql-boot")
                .termsOfServiceUrl("http://localhost:8081/")
                //.contact("wql")
                .version("1.0")
                .build();
    }
	
}
