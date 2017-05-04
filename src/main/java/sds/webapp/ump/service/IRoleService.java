/**
 * Title:IRoleService.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午3:09:15
**/
package sds.webapp.ump.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ump.domain.RoleDomain;

public interface IRoleService extends BaseService<RoleDomain>{
	public List<RoleDomain> getRoleByUser(UserDomain userDomain);

	public List<RoleDomain> findByWhereAll(RoleDomain roleDomain);
	
	public int changeUser(List<RoleDomain> list);

	public int deleteUserRole(List<RoleDomain> list);



}
