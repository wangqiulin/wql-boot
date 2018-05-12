package com.wql.boot.wqlboot.common.mymapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author wangqiulin
 * @date 2018年5月10日
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
	
	
}