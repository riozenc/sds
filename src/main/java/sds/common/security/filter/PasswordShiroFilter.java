package sds.common.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.riozenc.quicktool.common.util.http.HttpUtils;

import sds.common.security.token.UsernamePasswordToken;

@Service
public class PasswordShiroFilter extends FormAuthenticationFilter {
	private static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub

		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.getPrincipal());

		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);

		String host = HttpUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = WebUtils.getCleanParam(request, DEFAULT_CAPTCHA_PARAM);
		boolean mobile = WebUtils.isTrue(request, DEFAULT_MOBILE_PARAM);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// TODO Auto-generated method stub

		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)) {
			message = "用户或密码错误, 请重试.";
		} else if (e.getMessage() != null && e.getMessage().startsWith("msg:")) {
			message = e.getMessage().replace("msg:", "");
		} else {
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
		// request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute("message", message);

		return super.onLoginFailure(token, e, request, response);
		// return true;
	}
}
