package com.wql.boot.wqlboot.runner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.wql.boot.wqlboot.mapper.user.UserMapper;
import com.wql.boot.wqlboot.model.domain.user.User;

/**
 * 启动加载数据(数据字典， 系统参数等)
 * 
 * @author wangqiulin
 *
 */
@Component
@Order(value = 1)
public class StartUp1 implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(StartUp1.class);
    
    @Autowired
    private SqlSessionDaoSupport sqlSessionDaoSupport;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    public void run(String... args) throws Exception {
        logger.info(this.getClass().getName() + "启动加载数据" + args);
        //启动时获取SqlSession
        SqlSession sqlSession = sqlSessionDaoSupport.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = userMapper.selectAll();
        for (User user : list) {
        	redisTemplate.opsForValue().set(user.getDataId().toString(), user.getUserName(), 5, TimeUnit.MINUTES);
			System.out.println(user.getDataId()+"---"+user.getUserName());
		}
    }
    
}
