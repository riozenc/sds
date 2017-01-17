package sds.webapp.stm.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.stm.domain.ProfitMerchantDomain;

@TransactionDAO
public class ProfitMerchantDAO extends AbstractTransactionDAOSupport implements BaseDAO<ProfitMerchantDomain> {

	@Override
	public int delete(ProfitMerchantDomain profitMerchantDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", profitMerchantDomain);
	}

	@Override
	public ProfitMerchantDomain findByKey(ProfitMerchantDomain profitMerchantDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", profitMerchantDomain);
	}

	@Override
	public List<ProfitMerchantDomain> findByWhere(ProfitMerchantDomain profitMerchantDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", profitMerchantDomain);
	}

	@Override
	public int insert(ProfitMerchantDomain profitMerchantDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", profitMerchantDomain);
	}

	@Override
	public int update(ProfitMerchantDomain profitMerchantDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", profitMerchantDomain);
	}

}
