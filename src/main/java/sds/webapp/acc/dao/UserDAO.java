/**
 * 	Title:sds.webapp.acc.dao
 *		Datetime:2016年12月16日 下午5:55:59
 *		Author:czy
 */
package sds.webapp.acc.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.acc.domain.UserDomain;

@TransactionDAO
public class UserDAO extends AbstractTransactionDAOSupport implements BaseDAO<UserDomain> {

	@Override
	public int delete(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", userDomain);
	}

	@Override
	public UserDomain findByKey(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", userDomain);
	}

	@Override
	public List<UserDomain> findByWhere(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", userDomain);
	}

	@Override
	public int insert(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", userDomain);
	}

	@Override
	public int update(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", userDomain);
	}

	public UserDomain getUser(UserDomain userDomain) {
		return getPersistanceManager().load(getNamespace() + ".getUser", userDomain);
	}
}
