/**
 * 	Title:sds.webapp.acc.action
 *		Datetime:2016年12月16日 下午6:01:07
 *		Author:czy
 */
package sds.webapp.acc.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.UserService;

@ControllerAdvice
@RequestMapping("user")
public class UserAction {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@RequestMapping(params = "type=insert")
	public String insert(UserDomain userDomain) {
		if (userService.insert(userDomain) > 0) {
			return JSONUtil.writeSuccessMsg("添加成功.");
		} else {
			return JSONUtil.writeErrorMsg("添加失败.");
		}

	}
}
