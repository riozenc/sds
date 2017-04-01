/**
 * Title:BalanceMerchantLogDAO.java
 * Author:riozenc
 * Datetime:2017年3月7日 下午4:16:47
**/
package sds.webapp.blc.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.blc.domain.BalanceMerchantLogDomain;

@TransactionDAO
public class BalanceMerchantLogDAO extends AbstractTransactionDAOSupport implements BaseDAO<BalanceMerchantLogDomain> {

	@Override
	public int insert(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int delete(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public int update(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	@Override
	public BalanceMerchantLogDomain findByKey(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<BalanceMerchantLogDomain> findByWhere(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

	public String getCountBalanceByIn(BalanceMerchantLogDomain balanceMerchantLogDomain) {
		return getPersistanceManager().load(getNamespace() + ".getCountBalanceByIn", balanceMerchantLogDomain);
	}

	public List<BalanceMerchantLogDomain> getBalanceLogByUser(UserDomain userDomain) {
		return getPersistanceManager().find(getNamespace()+".getBalanceLogByUser", userDomain);
	}
}
