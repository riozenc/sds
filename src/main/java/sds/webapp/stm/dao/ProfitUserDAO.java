package sds.webapp.stm.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.stm.domain.ProfitUserDomain;

@TransactionDAO
public class ProfitUserDAO extends AbstractTransactionDAOSupport implements BaseDAO<ProfitUserDomain> {

	@Override
	public int delete(ProfitUserDomain profitUserDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", profitUserDomain);
	}

	@Override
	public ProfitUserDomain findByKey(ProfitUserDomain profitUserDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", profitUserDomain);
	}

	@Override
	public List<ProfitUserDomain> findByWhere(ProfitUserDomain profitUserDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", profitUserDomain);
	}

	@Override
	public int insert(ProfitUserDomain profitUserDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", profitUserDomain);
	}

	@Override
	public int update(ProfitUserDomain profitUserDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", profitUserDomain);
	}

}
