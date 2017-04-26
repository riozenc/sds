/**
 * Title:RoleAction.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午2:59:33
**/
package sds.webapp.ump.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonResult;
import sds.webapp.ump.domain.RoleDomain;
import sds.webapp.ump.service.IRoleService;

public class RoleAction {

	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;

	@ResponseBody
	@RequestMapping(params = "type=insert")
	public String insert(RoleDomain roleDomain) {
		int i = roleService.insert(roleDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=delete")
	public String delete(RoleDomain roleDomain) {
		int i = roleService.delete(roleDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(RoleDomain roleDomain) {
		int i = roleService.update(roleDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=findRoleByKey")
	public String findRoleByKey(RoleDomain roleDomain) {
		roleDomain = roleService.findByKey(roleDomain);
		return JSONUtil.toJsonString(roleDomain);
	}

	@ResponseBody
	@RequestMapping(params = "type=findRoleByWhere")
	public String findRoleByWhere(RoleDomain roleDomain) {
		List<RoleDomain> list = roleService.findByWhere(roleDomain);
		return JSONUtil.toJsonString(list);
	}
}
