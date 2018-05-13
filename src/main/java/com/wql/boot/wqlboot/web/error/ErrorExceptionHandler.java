package com.wql.boot.wqlboot.web.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author wangqiulin
 * @date 2018年5月12日
 */
@ControllerAdvice
public class ErrorExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorExceptionHandler.class);

	/**
	 * 统一异常处理
	 */
	@ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(RuntimeException exception) {
		logger.info("自定义异常处理-RuntimeException");
		ModelAndView m = new ModelAndView();
		m.addObject("exception", exception.getMessage());
		m.setViewName("error/500");
		return m;
	}

	
	/**
	 * 统一异常处理
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(Exception exception) {
		logger.info("自定义异常处理-Exception");
		ModelAndView m = new ModelAndView();
		m.addObject("exception", exception.getMessage());
		m.setViewName("error/500");
		return m;
	}
	
	
}
