package com.wql.boot.wqlboot.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wql.boot.wqlboot.common.constant.BaseConstant;
import com.wql.boot.wqlboot.common.constant.BusinessEnum;
import com.wql.boot.wqlboot.config.jwt.JwtHelper;
import com.wql.boot.wqlboot.config.jwt.JwtPatternUrl;
import com.wql.boot.wqlboot.config.jwt.JwtProperties;

import io.jsonwebtoken.Claims;

public class JwtFilter implements Filter {

	@Autowired
	private JwtProperties jwtProperty;

	@Autowired
	private JwtPatternUrl jwtPatternUrl;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if ("OPTIONS".equals(httpRequest.getMethod())) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}

		String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if (isInclude(url)) {
			// 如果是属于排除的URL，比如登录，注册，验证码等URL，则直接通行
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		String auth = httpRequest.getHeader("Authorization");
		if ((auth != null) && (auth.length() > 7)) {
			String HeadStr = auth.substring(0, 6).toLowerCase();
			if (HeadStr.compareTo("bearer") == 0) {
				auth = auth.substring(7, auth.length());
				Claims claims = JwtHelper.parseJWT(auth, jwtProperty.getBase64Secret());
				if (claims != null) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		// 验证不通过
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(BaseConstant.CODE, BusinessEnum.USER_NOT_LOGIN.getCode());
		resultMap.put(BaseConstant.MSG, BusinessEnum.USER_NOT_LOGIN.getMsg());
        ObjectMapper mapper = new ObjectMapper();
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
		httpResponse.getWriter().write(mapper.writeValueAsString(resultMap));
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
	}

	/**
	 * 是否需要过滤
	 * 
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		for (String patternUrl : jwtPatternUrl.getUrlPatterns()) {
			Pattern p = Pattern.compile(patternUrl);
			Matcher m = p.matcher(url);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}
}
