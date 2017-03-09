/**
 * Title:BalanceMerchantDAO.java
 * Author:riozenc
 * Datetime:2017年3月7日 下午3:48:37
**/
package sds.webapp.blc.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.blc.domain.BalanceMerchantDomain;

@TransactionDAO
public class BalanceMerchantDAO extends AbstractTransactionDAOSupport implements BaseDAO<BalanceMerchantDomain> {

	@Override
	public int insert(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int delete(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public int update(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	@Override
	public BalanceMerchantDomain findByKey(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<BalanceMerchantDomain> findByWhere(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

}
