package com.wql.boot.wqlboot.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wql.boot.wqlboot.domain.user.User;
import com.wql.boot.wqlboot.mapper.user.UserMapper;
import com.wql.boot.wqlboot.service.user.UserService;

import tk.mybatis.mapper.entity.Example;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	
	@Transactional
	@Override
	public void register(User user) {
		user.setId(null);
		int i = userMapper.insertSelective(user);
		if(i < 1) {
			throw new RuntimeException("注册失败");
		}
	}
	
	
	@Override
	public void login(String name, String password) {
		User record = new User();
		record.setUserName(name);
		User user = userMapper.selectOne(record);
		if(null == user) {
			return ;
		}
		if(!password.equals(user.getPassword())) {
			return ;
		}
	}
	
	
	@Cacheable(cacheNames="user", key = "#name")
	@Override
	public User queryByName(String name) {
		User record = new User();
		record.setUserName(name);
		User user = userMapper.selectOne(record);
		System.out.println("查询数据库了..........");
		return user;
	}
	
	
	@Transactional
	@CachePut(cacheNames="user", key = "#name")
	@Override
	public void updateByName(String name, String password) {
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("userName", name);
		
		User record = new User();
		record.setPassword(password);
		userMapper.updateByExampleSelective(record, example);
	}
	
	
	@Transactional
	@CacheEvict(cacheNames="user", key = "#name")
	@Override
	public void deleteByName(String name) {
		User record = new User();
		record.setUserName(name);
		userMapper.delete(record);
	}
	
	
}
