//package com.wql.boot.wqlboot.config.webmvc;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * 跨域问题解决
// * 在 spring MVC 中可以配置全局的规则，也可以使用@CrossOrigin 注解进行细粒度的配置。
// * 
// * @author wangqiulin
// * @date 2018年5月13日
// */
//@Configuration
//public class CustomCorsConfig {
//
//	/**
//	 * 全局配置
//	 * @return
//	 */
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				// 限制了路径和域名的访问
//				//registry.addMapping("/api/**").allowedOrigins("http://localhost:8081");
//			}
//		};
//	}
//	
//	
//	/**
//	 * 局部配置写法
//	 *  @CrossOrigin(origins = "http://localhost:8081")
//		@RequestMapping(value = "/get")
//		public HashMap<String, Object> get(@RequestParam String name) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("title", "hello world");
//			map.put("name", name);
//			return map;
//		}
//	 */
//	
//	
//}
