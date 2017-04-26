/**
 * Title:RegisterCorsFilter.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午1:26:36
**/
package sds.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCorsFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// getRegisterVerificationCode  请求时走这个方法
		// registerMerchant 请求时走这个方法
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String path = servletRequest.getQueryString();
		if(path!=null){
			if(path.indexOf("getRegisterVerificationCode")!=-1||path.indexOf("register")!=-1){
			servletResponse.addHeader("Access-Control-Allow-Origin", "*");
			servletResponse.addHeader("Access-Control-Allow-Methods", "POST, GET");
			servletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Requested-With, Origin");	
			}
		}
		chain.doFilter(request, response);
	}
}


