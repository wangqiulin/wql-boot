package com.wql.boot.wqlboot.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;


/**
 * 
 * @author wangqiulin
 * @date 2018年5月12日
 */
@JobHandler(value = "demoJobHandler")
@Component
public class DemoJobHandler extends IJobHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoJobHandler.class);
	
	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		try {
			logger.info("-----job.execute------");
		} catch (Exception e) {
			XxlJobLogger.log("出现异常:"+e);
            return FAIL;
		} 
		return SUCCESS;
	}
	
	
}
