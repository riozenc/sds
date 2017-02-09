package sds.webapp.sys.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.sys.domain.ConfDomain;

@TransactionDAO
public class ConfDAO extends AbstractTransactionDAOSupport implements BaseDAO<ConfDomain> {

	@Override
	public int delete(ConfDomain confDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", confDomain);
	}

	@Override
	public ConfDomain findByKey(ConfDomain confDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", confDomain);
	}

	@Override
	public List<ConfDomain> findByWhere(ConfDomain confDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", confDomain);
	}

	@Override
	public int insert(ConfDomain confDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", confDomain);
	}

	@Override
	public int update(ConfDomain confDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", confDomain);
	}

}
