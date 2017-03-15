package sds.webapp.acc.dao;

import java.util.List;
import java.util.Map;

import com.riozenc.quicktool.annotation.PaginationSupport;
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

	public MerchantDomain getUser(MerchantDomain merchantDomain) {
		return getPersistanceManager().load(getNamespace() + ".getUser", merchantDomain);
	}

	public Map<String, Object> checkRate(Integer id) {
		return getPersistanceManager().load(getNamespace() + ".checkRate", id);
	}

	public int insertPoolRel(Map<String, Integer> map) {
		return getPersistanceManager().insert(getNamespace() + ".insertPoolRel", map);
	}

	public int updatePoolRel(Map<String, Integer> map) {
		return getPersistanceManager().update(getNamespace() + ".updatePoolRel", map);
	}

	public int updatePool(MerchantDomain merchantDomain) {
		return getPersistanceManager().update(getNamespace() + ".updatePool", merchantDomain);
	}

	public List<MerchantDomain> getVirtualMerchants(MerchantDomain merchantDomain) {
		return getPersistanceManager().find(getNamespace() + ".getVirtualMerchants", merchantDomain);
	}

	public MerchantDomain getVirtualMerchant(MerchantDomain merchantDomain) {
		return getPersistanceManager().load(getNamespace() + ".getVirtualMerchant", merchantDomain);
	}

	public MerchantDomain getVirtualMerchantByKey(String account) {
		return getPersistanceManager().load(getNamespace() + ".getVirtualMerchantByKey", account);
	}

	public Map<String, String> getRAandVP(String account) {
		return getPersistanceManager().load(getNamespace() + ".getRAandVP", account);
	}

	@PaginationSupport
	public List<MerchantDomain> findMerchantByUser(MerchantDomain merchantDomain) {
		return getPersistanceManager().find(getNamespace() + ".findMerchantByUser", merchantDomain);
	}

	public int relievePoolRel(int merchantId) {
		return getPersistanceManager().update(getNamespace() + ".relievePoolRel", merchantId);
	}

}
