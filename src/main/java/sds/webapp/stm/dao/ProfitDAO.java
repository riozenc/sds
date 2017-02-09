package sds.webapp.stm.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.stm.domain.ProfitDomain;

@TransactionDAO
public class ProfitDAO extends AbstractTransactionDAOSupport implements BaseDAO<ProfitDomain> {

	@Override
	public int delete(ProfitDomain profitDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", profitDomain);
	}

	@Override
	public ProfitDomain findByKey(ProfitDomain profitDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", profitDomain);
	}

	@Override
	public List<ProfitDomain> findByWhere(ProfitDomain profitDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", profitDomain);
	}

	@Override
	public int insert(ProfitDomain profitDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", profitDomain);
	}

	@Override
	public int update(ProfitDomain profitDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", profitDomain);
	}

	public int insertBatch(List<ProfitDomain> list) {
		return getPersistanceManager(ExecutorType.BATCH).insertList(getNamespace() + ".insert", list);
	}

	public List<ProfitDomain> getAllProfit(ProfitDomain profitDomain) {
		return getPersistanceManager().find(getNamespace() + ".getAllProfit", profitDomain);
	}

	public int profitCountComplete(List<ProfitDomain> list) {
		return getPersistanceManager(ExecutorType.BATCH).updateList(getNamespace() + ".profitCountComplete", list);
	}

}
