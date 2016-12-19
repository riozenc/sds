package sds.webapp.sys.action;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

@ControllerAdvice
@RequestMapping("loginAction")
public class LoginAction {

	@ResponseBody
	@RequestMapping(value = "login")
	public String login() {
		if (true) {

			return JSONUtil.writeSuccessMsg("登录成功...");
		} else {
			return "login.html";
		}
	}
}
