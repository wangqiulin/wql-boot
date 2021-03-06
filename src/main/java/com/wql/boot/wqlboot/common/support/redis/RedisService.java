package com.wql.boot.wqlboot.common.support.redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.wql.boot.wqlboot.common.util.date.DateUtil;


@Component
public class RedisService {

	@Resource(name="redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	//-------------------设置功能---------------------//
	/**永久设置*/
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**永久设置,json格式保存*/
	public void setJson(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**带有效时间的设置, 时间单位为秒*/
	public void setWithExByS(String key, String value, long seconds) {
		redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
	}
	
	/**带有效时间的设置, 时间单位为毫秒*/
	public void setWithExByMS(String key, String value, long milliseconds) {
		redisTemplate.opsForValue().set(key, value, milliseconds, TimeUnit.MILLISECONDS);
	}
	
	/**设置hash结构值*/
	public void hashSet(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	/**在hashKey已经存在的情况下, putIfAbsent下不会进入修改value*/
	public void hashSetIfAbsent(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
	}
	
	/**设置当天才有效的值*/
	public boolean setWithCurrentDay(String key, String value) {
		try {
			//两个日期之间的差(毫秒值之差)
			String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String currentLastTime = format + " 23:59:59";
			long between = DateUtil.between(new Date(), DateUtil.transString2Date(currentLastTime));  
			redisTemplate.opsForValue().set(key, value, between, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	//-------------------获取功能---------------------//
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	public String getStr(String key) {
		return (String)redisTemplate.opsForValue().get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObj(String key, Class<T> clazz) {
		return (T)redisTemplate.opsForValue().get(key);
	}
	
	public Object hashGet(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	
	
	//-------------------删除功能---------------------//
	
	/**hash结构数据删除, 返回删除成功的个数*/
	public Long hashDel(String key, Object... hashKeys) {
		return redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	
	//-------------------自增长和自增减---------------------//
	/**按1自增*/
	public long incr(String key) {  
		return redisTemplate.opsForValue().increment(key, 1);  
    }  
	
	
	/**自增 或 自减*/
	public long incrByNum(String key, Integer byNum) {  
		return redisTemplate.opsForValue().increment(key, byNum);  
    }  
	
	
	//-------------------获取剩余有效时间---------------------//
	/**获取剩余有效时间*/
	public long getExpireByKey(String key) {
		return redisTemplate.opsForValue().getOperations().getExpire(key);
	}
	
	
}
