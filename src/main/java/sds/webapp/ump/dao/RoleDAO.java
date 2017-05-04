/**
 * Title:RoleDAO.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午4:09:43
**/
package sds.webapp.ump.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ump.domain.RoleDomain;

@TransactionDAO
public class RoleDAO extends AbstractTransactionDAOSupport implements BaseDAO<RoleDomain> {

	@Override
	public int delete(RoleDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public RoleDomain findByKey(RoleDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<RoleDomain> findByWhere(RoleDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

	@Override
	public int insert(RoleDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int update(RoleDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	public List<RoleDomain> getRoleByUser(UserDomain userDomain) {
		return getPersistanceManager().find(getNamespace() + ".getRoleByUser", userDomain);
	}

	public List<RoleDomain> findByWhereAll(RoleDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhereAll", t);
	}

	public int changeUser(List<RoleDomain> list) {
		// TODO Auto-generated method stub
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".changeUser", list);
	}

	public int deleteUserRole(List<RoleDomain> list) {
		return getPersistanceManager(ExecutorType.BATCH).deleteList(getNamespace() + ".deleteUserRole", list);
	}
}
