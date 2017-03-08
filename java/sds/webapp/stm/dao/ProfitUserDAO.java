package sds.webapp.stm.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;

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

	public int insertBatch(List<ProfitUserDomain> list) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert", list);
	}

	public List<ProfitUserDomain> findProfitUserByWhere(ProfitUserDomain profitUserDomain) {
		return getPersistanceManager().find(getNamespace() + ".findProfitUserByWhere", profitUserDomain);
	}

	// 获取下级分润
	public List<ProfitUserDomain> findSubProfitUserByWhere(ProfitUserDomain profitUserDomain) {
		return getPersistanceManager().find(getNamespace() + ".findSubProfitUserByWhere", profitUserDomain);
	}

	// 获取每一天分润
	public List<ProfitUserDomain> findDateProfitUserByWhere(ProfitUserDomain profitUserDomain) {
		return getPersistanceManager().find(getNamespace() + ".findDateProfitUserByWhere", profitUserDomain);
	}
}
