package com.dx.springlearn.handlers.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 *  拦截
 *
 */
public class MInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object o, Exception e) throws Exception {
		httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods","POST");
		httpServletResponse.setHeader("Access-Control-Allow-Headers","Access-Control");
		httpServletResponse.setHeader("Allow","POST");
		httpServletResponse.setHeader("User-Agent", "imgfornote");
		httpServletResponse.setHeader("Content-Type", "application/json; charset=UTF-8");
	}


}
