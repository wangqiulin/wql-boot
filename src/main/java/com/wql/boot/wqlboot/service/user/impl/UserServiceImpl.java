package com.wql.boot.wqlboot.service.user.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wql.boot.wqlboot.common.constant.BusinessEnum;
import com.wql.boot.wqlboot.common.constant.BusinessException;
import com.wql.boot.wqlboot.common.constant.DataResponse;
import com.wql.boot.wqlboot.common.support.cat.CatTransaction;
import com.wql.boot.wqlboot.common.util.bean.BeanUtils;
import com.wql.boot.wqlboot.common.util.pwd.PwdEncoderUtil;
import com.wql.boot.wqlboot.config.jwt.JwtHelper;
import com.wql.boot.wqlboot.config.jwt.JwtProperties;
import com.wql.boot.wqlboot.mapper.user.UserMapper;
import com.wql.boot.wqlboot.model.domain.user.User;
import com.wql.boot.wqlboot.model.req.user.UserLoginReq;
import com.wql.boot.wqlboot.model.req.user.UserRegisterReq;
import com.wql.boot.wqlboot.model.req.user.UserUpdateReq;
import com.wql.boot.wqlboot.model.res.user.UserExcelResult;
import com.wql.boot.wqlboot.service.user.UserService;
import com.xuxueli.poi.excel.ExcelExportUtil;

/**
 * 
 * @Cacheable(cacheNames="user", key = "#name")
 * @CachePut(cacheNames="user", key = "#name")
 * @CacheEvict(cacheNames="user", key = "#name")
 */
@Service
@CatTransaction
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtProperties jwtProperties;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public DataResponse register(UserRegisterReq req) {
		Assert.isTrue(StringUtils.isNotBlank(req.getUserName()), "用户名不能为空");
		Assert.isTrue(StringUtils.isNotBlank(req.getPassword()), "密码不能为空");

		User record = new User();
		record.setUserName(req.getUserName());
		// 查询是否存在
		User user = userMapper.selectOne(record);
		if (user != null) {
			throw new BusinessException(BusinessEnum.USER_EXIST);
		}
		// 保存
		record.setId(null);
		record.setUserPwd(PwdEncoderUtil.encrypt(req.getPassword()));
		record.setCreateDate(new Date());
		record.setUpdateDate(record.getCreateDate());
		int flag = userMapper.insertSelective(record);
		if (flag != 1) {
			throw new BusinessException(BusinessEnum.USER_REGISTER_FAIL);
		}
		return new DataResponse(BusinessEnum.SUCCESS);
	}

	@Override
	public DataResponse login(UserLoginReq req) {
		Assert.isTrue(StringUtils.isNotBlank(req.getUserName()), "用户名不能为空");
		Assert.isTrue(StringUtils.isNotBlank(req.getPassword()), "密码不能为空");

		User record = new User();
		record.setUserName(req.getUserName());
		User user = userMapper.selectOne(record);
		if (user == null) {
			throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
		}
		// 判断密码是否正确
		if (!PwdEncoderUtil.match(req.getPassword(), user.getUserPwd())) {
			throw new BusinessException(BusinessEnum.USER_PWD_ERROR);
		}
		// 登录成功
		String jwtToken = JwtHelper.createJWT(req.getUserName(), user.getId() + "", "role", jwtProperties.getClientId(),
				jwtProperties.getName(), jwtProperties.getExpiresSecond() * 1000, jwtProperties.getBase64Secret());
		return new DataResponse(BusinessEnum.SUCCESS, "bearer;" + jwtToken);
	}

	@Override
	public DataResponse queryUser(String userName) {
		Assert.isTrue(StringUtils.isNotBlank(userName), "用户名不能为空");

		User record = new User();
		record.setUserName(userName);
		User user = userMapper.selectOne(record);
		if (user == null) {
			throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
		}
		return new DataResponse(BusinessEnum.SUCCESS, user);
	}

	@Override
	@Cacheable(cacheNames="CacheData:user")
	public DataResponse queryAll() {
		List<User> list = userMapper.selectAll();
		return new DataResponse(BusinessEnum.SUCCESS, list);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public DataResponse updateUser(UserUpdateReq req) {
		Assert.isTrue(req.getDataId() != null, "dataId不能为空");

		User record = new User();
		record.setId(req.getDataId());
		record.setUserName(req.getUserName());
		if (StringUtils.isNotBlank(req.getUserPwd())) {
			record.setUserPwd(PwdEncoderUtil.encrypt(req.getUserPwd()));
		}
		int flag = userMapper.updateByPrimaryKeySelective(record);
		return flag == 1 ? new DataResponse(BusinessEnum.SUCCESS) : new DataResponse(BusinessEnum.FAIL);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public DataResponse deleteUser(Integer dataId) {
		Assert.isTrue(dataId != null, "dataId不能为空");
		int flag = userMapper.deleteByPrimaryKey(dataId);
		return flag == 1 ? new DataResponse(BusinessEnum.SUCCESS) : new DataResponse(BusinessEnum.FAIL);
	}

	@Override
	public void export(String filePath) {
		// 创建文件
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		// 查询数据
		List<User> list = userMapper.selectAll();
		List<UserExcelResult> resultList = BeanUtils.copyByList(list, UserExcelResult.class);
		// 导出
		ExcelExportUtil.exportToFile(filePath, resultList);
	}

}
