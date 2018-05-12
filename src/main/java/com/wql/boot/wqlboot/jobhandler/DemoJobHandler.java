package com.wql.boot.wqlboot.jobhandler;

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
	
	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		XxlJobLogger.log("---begin-->");
		try {
			System.out.println("=====demoJobHandler=======");
		} catch (Exception e) {
			XxlJobLogger.log("出现异常:"+e);
            return FAIL;
		} finally {
			XxlJobLogger.log("---end-->");
		}
		return SUCCESS;
	}
	
	
}
