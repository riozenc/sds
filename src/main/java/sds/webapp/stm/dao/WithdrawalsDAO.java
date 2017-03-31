/**
 * Title:WithdrawalsDAO.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:24:55
**/
package sds.webapp.stm.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.stm.domain.WithdrawalsDomain;

@TransactionDAO
public class WithdrawalsDAO extends AbstractTransactionDAOSupport implements BaseDAO<WithdrawalsDomain> {

	@Override
	public int delete(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", withdrawalsDomain);
	}

	@Override
	public WithdrawalsDomain findByKey(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", withdrawalsDomain);
	}

	@Override
	public List<WithdrawalsDomain> findByWhere(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", withdrawalsDomain);
	}

	@Override
	public int insert(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", withdrawalsDomain);
	}

	@Override
	public int update(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", withdrawalsDomain);
	}

	public List<WithdrawalsDomain> getwithdrawals(WithdrawalsDomain withdrawalsDomain) {
		return getPersistanceManager().find(getNamespace() + ".getwithdrawals", withdrawalsDomain);
	}

}
