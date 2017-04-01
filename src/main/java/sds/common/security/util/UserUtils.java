package sds.common.security.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.riozenc.quicktool.exception.LoginTimeOutException;

import sds.common.security.Principal;

public class UserUtils {
	public static Principal getPrincipal() {
		Subject subject = SecurityUtils.getSubject();
		Principal principal = (Principal) subject.getPrincipal();

		if (principal == null) {
			throw new LoginTimeOutException("未登录...");
		}
		return principal;
	}

	public static boolean hasRole(String role) {
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole(role);
	}
}
