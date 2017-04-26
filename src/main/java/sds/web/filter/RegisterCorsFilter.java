/**
 * Title:RegisterCorsFilter.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午1:26:36
**/
package sds.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RegisterCorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
		// getRegisterVerificationCode
		// registerMerchant
		
		
		
		
		
//		if(){
//			httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//			httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST, GET");
//			httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Requested-With, Origin");	
//		}
		
		chain.doFilter(request, response);
	}

}
