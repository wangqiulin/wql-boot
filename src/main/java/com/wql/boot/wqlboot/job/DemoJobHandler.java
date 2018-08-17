package com.wql.boot.wqlboot.job;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.wql.boot.wqlboot.mapper.user.UserMapper;
import com.wql.boot.wqlboot.model.domain.user.User;
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
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		try {
			logger.info("-----job.execute------");
			List<String> delList = new ArrayList<>();
			List<User> list = userMapper.selectAll();
			for (User user : list) {
				delList.add(user.getDataId().toString());
			}
			redisTemplate.delete(delList);
		} catch (Exception e) {
			XxlJobLogger.log("出现异常:"+e);
            return FAIL;
		} 
		return SUCCESS;
	}
	
	
}
