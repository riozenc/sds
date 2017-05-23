/**
 * Title:RoleAction.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午2:59:33
**/
package sds.webapp.ump.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonResult;
import sds.webapp.ump.domain.RoleDomain;
import sds.webapp.ump.service.IRoleService;

@ControllerAdvice
@RequestMapping("role")

public class RoleAction {

	@Autowired
	@Qualifier("roleServiceImpl")
	private IRoleService roleService;

	@ResponseBody
	@RequestMapping(params = "type=insert")
	public String insert(RoleDomain roleDomain, String rankValue) {
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
		// System.out.println(JSONUtil.toJsonString(httpServletRequest.getParameterMap()));
		List<RoleDomain> listAll = roleService.findByWhereAll(roleDomain);
		List<RoleDomain> list = roleService.findByWhere(roleDomain);
		Set<RoleDomain> ts = new HashSet<RoleDomain>();
		if (list.size() < 1) {
			System.out.println(list.size());
			ts.addAll(listAll);
			return JSONUtil.toJsonString(ts);
		}
		for (RoleDomain c : list) {
			c.setIsCheck(c.getRoleId());
			c.setRoleId(c.getRoleId());
		}
		listAll.removeAll(list);
		listAll.addAll(list);
		ts.addAll(listAll);
		return JSONUtil.toJsonString(ts);
	}
	@ResponseBody
	@RequestMapping(params = "type=changeUser")
	public String changeUser(RoleDomain roleDomain, String rankValue,String userId) {
		roleDomain.setId(Integer.valueOf(userId));
		String[] values = rankValue.split(",");
		List<RoleDomain> list = new ArrayList<>();
		for (String roleId : values) {
			RoleDomain role = new RoleDomain();
			role.setUserId(roleDomain.getId());
			if(Integer.parseInt(roleId)!=0){
				role.setRoleId(Integer.parseInt(roleId));
			}
			list.add(role);
		}
		roleService.deleteUserRole(list); //删除所有权限
		int i = roleService.changeUser(list);//根据权限添加
		if (i>0){
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "失败."));
		}
}
}
