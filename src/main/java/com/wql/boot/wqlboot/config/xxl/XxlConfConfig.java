//package com.wql.boot.wqlboot.config.xxl;
//
//import com.xxl.conf.core.spring.XxlConfFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 
// * @author wangqiulin
// * @date 2018年5月12日
// */
//@Configuration
//public class XxlConfConfig {
//	
//    private Logger logger = LoggerFactory.getLogger(XxlConfConfig.class);
//
//    @Bean(initMethod = "init", destroyMethod = "destroy")
//    public XxlConfFactory xxlConfFactory() {
//        XxlConfFactory xxlConf = new XxlConfFactory();
//        logger.info(">>>>>>>>>>> xxl-conf config init.");
//        return xxlConf;
//    }
//
//}