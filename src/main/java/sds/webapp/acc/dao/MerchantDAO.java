package sds.webapp.acc.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.acc.domain.MerchantDomain;

@TransactionDAO
public class MerchantDAO extends AbstractTransactionDAOSupport implements BaseDAO<MerchantDomain> {

	@Override
	public int insert(MerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int delete(MerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public int update(MerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	@Override
	public MerchantDomain findByKey(MerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<MerchantDomain> findByWhere(MerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

	public int updateRate(MerchantDomain merchantDomain) {
		return getPersistanceManager().update(getNamespace() + ".updateRate", merchantDomain);
	}

	public List<MerchantDomain> getAllCheckedMerchant() {
		return getPersistanceManager().find(getNamespace() + ".getAllCheckedMerchant", null);
	}
}
