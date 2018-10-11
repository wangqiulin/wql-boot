package com.wql.boot.wqlboot.runner;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.wql.boot.wqlboot.mapper.user.UserMapper;

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
	@Qualifier("userMapper")
    private SqlSessionDaoSupport sqlSessionDaoSupport;
    
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    public void run(String... args) throws Exception {
        logger.info("【启动加载数据】");
        //启动时获取SqlSession
        
        //方式1
        SqlSession sqlSession = sqlSessionDaoSupport.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.selectAll();
        
        //方式2
        SqlSession sqlSession2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
        	userMapper = sqlSession2.getMapper(UserMapper.class);
        	//TODO 批量处理
        	boolean flag = false;
            if (flag) {
            	sqlSession2.rollback();
            } else {
            	sqlSession2.commit();
            }
            sqlSession2.clearCache();
        } catch (Exception e) {
        	sqlSession2.rollback();
        } finally {
        	sqlSession2.close();
        }
        
       
    }
    
}
