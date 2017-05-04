/**
 * Title:RoleServiceImpl.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午4:05:10
**/
package sds.webapp.ump.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ump.dao.RoleDAO;
import sds.webapp.ump.domain.RoleDomain;
import sds.webapp.ump.service.IRoleService;

@TransactionService
public class RoleServiceImpl implements IRoleService {

	@TransactionDAO
	private RoleDAO roleDAO;

	@Override
	public int insert(RoleDomain t) {
		// TODO Auto-generated method stub
		return roleDAO.insert(t);
	}

	@Override
	public int delete(RoleDomain t) {
		// TODO Auto-generated method stub
		return roleDAO.delete(t);
	}

	@Override
	public int update(RoleDomain t) {
		// TODO Auto-generated method stub
		return roleDAO.update(t);
	}

	@Override
	public RoleDomain findByKey(RoleDomain t) {
		// TODO Auto-generated method stub
		return roleDAO.findByKey(t);
	}

	@Override
	public List<RoleDomain> findByWhere(RoleDomain t) {
		// TODO Auto-generated method stub
		return roleDAO.findByWhere(t);
	}
	
	public List<RoleDomain> getRoleByUser(UserDomain userDomain) {
		return roleDAO.getRoleByUser(userDomain);
	}
	@Override
	public List<RoleDomain> findByWhereAll(RoleDomain t) {
		// TODO Auto-generated method stub
		return roleDAO.findByWhereAll(t);
	}

	@Override
	public int changeUser(List<RoleDomain> list) {
		// TODO Auto-generated method stub
		//roleDAO.deleteUserRole(list);
		return roleDAO.changeUser(list);
	}
	@Override
	public int deleteUserRole(List<RoleDomain> list) {
		// TODO Auto-generated method stub
		return roleDAO.deleteUserRole(list);
	}

}
