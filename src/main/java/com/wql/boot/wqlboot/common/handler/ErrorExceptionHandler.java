package com.wql.boot.wqlboot.common.handler;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.wql.boot.wqlboot.common.constant.BusinessException;
import com.wql.boot.wqlboot.common.enums.BusinessEnum;

/**
 * 异常处理机制
 * 
 * @author wangqiulin
 * @date 2018年5月12日
 */
@ControllerAdvice
public class ErrorExceptionHandler {

	/** 自定义业务异常处理  */
	@ExceptionHandler({ BusinessException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(BusinessException busExp) {
		 ModelAndView mv = new ModelAndView();
         //使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常	
         FastJsonJsonView view = new FastJsonJsonView();
         Map<String, Object> attributes = new HashMap<String, Object>();
         attributes.put("code", busExp.getCode());
         attributes.put("msg", busExp.getMsg());
         view.setAttributesMap(attributes);
         mv.setView(view); 
         return mv;
	}
	
	/** 参数异常处理  */
	@ExceptionHandler({ ValidationException.class, IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException() {
		 ModelAndView mv = new ModelAndView();
         FastJsonJsonView view = new FastJsonJsonView();
         Map<String, Object> attributes = new HashMap<String, Object>();
         attributes.put("code", BusinessEnum.PARAM_FAIL.getCode());
         attributes.put("msg", BusinessEnum.PARAM_FAIL.getMsg());
         view.setAttributesMap(attributes);
         mv.setView(view); 
         return mv;
	}
	
	/** 统一异常处理 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(Exception exp) {
		ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("code", BusinessEnum.FAIL.getCode());
        attributes.put("msg", BusinessEnum.FAIL.getMsg());
        view.setAttributesMap(attributes);
        mv.setView(view); 
        return mv;
	}
	
}
