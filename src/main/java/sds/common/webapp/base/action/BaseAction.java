/**
 * Title:BaseAction.java
 * Author:czy
 * Datetime:2016年9月18日 下午5:09:38
 */
package sds.common.webapp.base.action;

import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

public class BaseAction extends com.riozenc.quicktool.springmvc.webapp.action.BaseAction {

	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String processUnauthenticatedException(NativeWebRequest request, UnauthenticatedException e) {
		System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");
		return "viewName"; // 返回一个逻辑视图名
	}

}
