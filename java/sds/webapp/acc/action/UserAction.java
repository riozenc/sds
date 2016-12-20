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
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.UserService;

@ControllerAdvice
@RequestMapping("user")
public class UserAction extends BaseAction {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@ResponseBody
	@RequestMapping(params = "type=insert")
	public String insert(UserDomain userDomain) {

		userDomain.setCreateId(UserUtils.getPrincipal().getUserId());
		int i = userService.insert(userDomain);
		if (i > 0) {
			//statusCode message
			//count psize list
			return JSONUtil.writeSuccessMsg("添加成功.");
		} else {
			return JSONUtil.writeErrorMsg("添加失败.");
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(UserDomain userDomain) {
		if (userService.update(userDomain) > 0) {
			return JSONUtil.writeSuccessMsg("更新成功.");
		} else {
			return JSONUtil.writeSuccessMsg("更新失败.");
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=findUserByWhere")
	public String findUserByWhere(UserDomain userDomain) {

		return JSONUtil.getJsonResult(userService.findByWhere(userDomain));
	}
}
