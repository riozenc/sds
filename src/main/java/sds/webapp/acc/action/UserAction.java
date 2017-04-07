/**
 * 	Title:sds.webapp.acc.action
 *		Datetime:2016年12月16日 下午6:01:07
 *		Author:czy
 */
package sds.webapp.acc.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.UserService;

/**
 * 代理商
 * 
 * @author riozenc
 *
 */
@ControllerAdvice
@RequestMapping("user")
public class UserAction extends BaseAction {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	/**
	 * 新增代理商
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=insert")
	public String insert(UserDomain userDomain) {
		userDomain.setCreateId(UserUtils.getPrincipal().getUserId());
		int i = userService.insert(userDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "新增成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "新增失败."));
		}
	}

	/**
	 * 更新代理商信息
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(UserDomain userDomain) {
		if (userService.update(userDomain) > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新失败."));
		}
	}

	/**
	 * 删除代理商
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=delete")
	public String delete(UserDomain userDomain) {
		if (userService.delete(userDomain) > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "删除成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "删除失败."));
		}
	}

	/**
	 * 审核代理商
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=checkUser")
	public String checkUser(UserDomain userDomain) {

		if (userService.checkUser(userDomain) > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "审核成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "审核失败."));
		}

	}

	/**
	 * 修改代理商费率
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=updateRate")
	public String updateRate(UserDomain userDomain) {

		// 审核后的代理商信息不能被修改
		if (userDomain.getStatus() == 1) {
			return "该代理商已被审核，相关信息无法修改.";
		}

		if (userService.updateRate(userDomain) > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=findMeInfo")
	public String findMeInfo() {
		UserDomain userDomain = UserUtils.getPrincipal().getUserDomain();
		return JSONUtil.toJsonString(userDomain);
	}

	/**
	 * 查询指定代理商信息
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findUserByKey")
	public String findUserByKey(UserDomain userDomain) {
		
		return JSONUtil.toJsonString(userService.findByKey(userDomain));
	}

	/**
	 * 查询代理商
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findUserByWhere")
	public String findUserByWhere(UserDomain userDomain) {
		userDomain.setId(UserUtils.getPrincipal().getUserDomain().getId());
		List<UserDomain> list = userService.findSubUserList(userDomain);
		return JSONUtil.toJsonString(new JsonGrid(userDomain, list));
	}

}
