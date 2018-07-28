package com.wql.boot.wqlboot.service.user.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wql.boot.wqlboot.common.constant.BusinessException;
import com.wql.boot.wqlboot.common.constant.DataResponse;
import com.wql.boot.wqlboot.common.enums.BusinessEnum;
import com.wql.boot.wqlboot.common.util.pwd.PwdEncoderUtil;
import com.wql.boot.wqlboot.mapper.user.UserMapper;
import com.wql.boot.wqlboot.model.domain.user.User;
import com.wql.boot.wqlboot.model.req.user.UserLoginReq;
import com.wql.boot.wqlboot.model.req.user.UserRegisterReq;
import com.wql.boot.wqlboot.model.req.user.UserUpdateReq;
import com.wql.boot.wqlboot.service.user.UserService;

/**
 *
 * @author wangqiulin
 * @date 2018年5月10日
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	
	@Transactional(rollbackFor=RuntimeException.class)
	@Override
	public DataResponse register(UserRegisterReq req) {
		User record = new User();
		record.setUserName(req.getUserName());
		//查询是否存在
		User user = userMapper.selectOne(record);
		if(user != null) {
			throw new BusinessException(BusinessEnum.USER_EXIST);
		}
		//保存
		record.setDataId(null);
		record.setUserPwd(PwdEncoderUtil.encrypt(req.getPassword()));
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		int flag = userMapper.insertSelective(record);
		if(flag != 1) {
			throw new BusinessException(BusinessEnum.USER_REGISTER_FAIL);
		}
		return new DataResponse(BusinessEnum.SUCCESS);
	}
	
	
	@Override
	public DataResponse login(UserLoginReq req) {
		User record = new User();
		record.setUserName(req.getUserName());
		User user = userMapper.selectOne(record);
		if(user == null) {
			throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
		}
		if(!PwdEncoderUtil.match(req.getPassword(), user.getUserPwd())) {
			throw new BusinessException(BusinessEnum.USER_PWD_ERROR);
		}
		//登录成功
		return new DataResponse(BusinessEnum.SUCCESS);
	}
	
	
	@Override
	public DataResponse queryUser(String userName) {
		User record = new User();
		record.setUserName(userName);
		User user = userMapper.selectOne(record);
		if(user == null) {
			throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
		}
		return new DataResponse(BusinessEnum.SUCCESS, user);
	}
	
	
	@Override
	public DataResponse queryAll() {
		List<User> list = userMapper.selectAll();
		return new DataResponse(BusinessEnum.SUCCESS, list);
	}
	
	@Override
	public DataResponse updateUser(UserUpdateReq req) {
		User record = new User();
		record.setDataId(req.getDataId());
		record.setUserName(req.getUserName());
		if(StringUtils.isNotBlank(req.getUserPwd())) {
			record.setUserPwd(PwdEncoderUtil.encrypt(req.getUserPwd()));
		}
		int flag = userMapper.updateByPrimaryKeySelective(record);
		return flag == 1 ? new DataResponse(BusinessEnum.SUCCESS) : new DataResponse(BusinessEnum.FAIL);
	}
	
	
	@Override
	public DataResponse deleteUser(Integer dataId) {
		int flag = userMapper.deleteByPrimaryKey(dataId);
		return flag == 1 ? new DataResponse(BusinessEnum.SUCCESS) : new DataResponse(BusinessEnum.FAIL);
	}
	
	
	/*@Cacheable(cacheNames="user", key = "#name")
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
	}*/
	
	
}
