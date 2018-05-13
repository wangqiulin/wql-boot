package com.wql.boot.wqlboot.common.support.httpclient;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author wangqiulin
 * @date 2018年5月13日
 */
@Component
public class HttpService {

	private static final Logger logger = LoggerFactory.getLogger(HttpService.class);
	
	private static final String GET = "get";
	private static final String POST = "post";
	
	@Autowired
	private RestTemplate restTemplate;

	
	/**
	 * post的表单请求
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public String postForEntity(String url, Map<String, String> paramMap) {
		HttpHeaders headers = new HttpHeaders();
		//请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		if(!CollectionUtils.isEmpty(paramMap)){
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				params.add(entry.getKey(), entry.getValue());
			}
		}
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return response.getBody();
	}
	
	
	/**
	 * 远程接口调用方法，返回String类型的json字符串格式数据
	 */
	public String exchange(String url, String method) {
		return this.exchange(url, method, null, null);
	}
	
	
	/**
	 * 远程接口调用方法，返回String类型的json字符串格式数据
	 */
	public String exchange(String url, String method, Map<String, Object> params) {
		return this.exchange(url, method, params, null);
	}
	

	/**
	 * 远程接口调用方法，返回String类型的json字符串格式数据
	 */
	public String exchange(String url, String method, Map<String, Object> params, Map<String, Object> headerParams) {
		try {
			// 调用外部接口入口
			ResponseEntity<String> responseEntity = this.doRequest(url, method, JSON.toJSONString(params), headerParams);
			if (responseEntity != null) {
				return responseEntity.getBody();
			}
		} catch (Exception e) {
			logger.error("外部系统异常", e);
		}
		return null;
	}

	/* 调用，以及日志打印 */
	private ResponseEntity<String> doRequest(String url, String method, 
			String jsonString, Map<String, Object> headerParamsMap) {
		HttpHeaders headers = this.getHttpHeaders(headerParamsMap);
		logger.info(String.format("调用外部接口，请求参数: url=%s, method=%s, body=%s, header=%s", 
				url, method, jsonString, headers.toString()));
		ResponseEntity<String> responseEntity = null;
		if (POST.equalsIgnoreCase(method)) {
			responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(jsonString, headers), String.class);
		}
		if (GET.equalsIgnoreCase(method)) {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(jsonString, headers), String.class);
		}
		if(responseEntity != null){
			logger.info(String.format("调用外部接口，原始返回报文: data=%s", responseEntity.toString()));
		}else{
			logger.info("ResponseEntity<String> responseEntity为空!");
		}
		return responseEntity;
	}

	
	/* 设置请求头信息 */
	private HttpHeaders getHttpHeaders(Map<String, Object> headerParamsMap) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json; charset=utf-8"); 
		headers.add("cache-control", "no-cache");  
		// 设置额外需要的请求头参数
		if (!CollectionUtils.isEmpty(headerParamsMap)) {
			for (Map.Entry<String, Object> entry : headerParamsMap.entrySet()) {
				headers.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return headers;
	}
	
	
}
