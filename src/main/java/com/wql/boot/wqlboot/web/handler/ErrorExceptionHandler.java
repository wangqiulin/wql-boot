package com.wql.boot.wqlboot.web.handler;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.wql.boot.wqlboot.common.constant.BusinessEnum;
import com.wql.boot.wqlboot.common.constant.BusinessException;

/**
 * 异常处理机制
 * 
 * @author wangqiulin
 * @date 2018年5月12日
 */
@ControllerAdvice
public class ErrorExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorExceptionHandler.class);
	
	private static final String CODE = "code";
	private static final String MSG = "msg";
	

	/** 自定义业务异常处理  */
	@ExceptionHandler({ BusinessException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(BusinessException busExp) {
		 logger.error("【业务异常】：", busExp);
		 ModelAndView mv = new ModelAndView();
         //使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常	
         FastJsonJsonView view = new FastJsonJsonView();
         Map<String, Object> attributes = new HashMap<String, Object>();
         attributes.put(CODE, busExp.getCode());
         attributes.put(MSG, busExp.getMsg());
         view.setAttributesMap(attributes);
         mv.setView(view); 
         return mv;
	}
	
	/** 参数异常处理  */
	@ExceptionHandler({ ValidationException.class, IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException() {
		 logger.error("【参数异常】");
		 ModelAndView mv = new ModelAndView();
         FastJsonJsonView view = new FastJsonJsonView();
         Map<String, Object> attributes = new HashMap<String, Object>();
         attributes.put(CODE, BusinessEnum.PARAM_FAIL.getCode());
         attributes.put(MSG, BusinessEnum.PARAM_FAIL.getMsg());
         view.setAttributesMap(attributes);
         mv.setView(view); 
         return mv;
	}
	
	/** 统一异常处理 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(Exception exp) {
		logger.error("【系统异常】：", exp);
		ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(CODE, BusinessEnum.FAIL.getCode());
        attributes.put(MSG, BusinessEnum.FAIL.getMsg());
        view.setAttributesMap(attributes);
        mv.setView(view); 
        return mv;
	}
	
}
