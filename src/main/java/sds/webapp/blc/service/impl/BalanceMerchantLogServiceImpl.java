/**
 * Title:BalanceMerchantLogDomain.java
 * Author:riozenc
 * Datetime:2017年3月7日 下午4:16:03
**/
package sds.webapp.blc.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.blc.dao.BalanceMerchantLogDAO;
import sds.webapp.blc.domain.BalanceMerchantLogDomain;
import sds.webapp.blc.service.BalanceMerchantLogService;

@TransactionService
public class BalanceMerchantLogServiceImpl implements BalanceMerchantLogService {

	@TransactionDAO
	private BalanceMerchantLogDAO balanceMerchantLogDAO;

	@Override
	public int insert(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.insert(t);
	}

	@Override
	public int delete(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.delete(t);
	}

	@Override
	public int update(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.update(t);
	}

	@Override
	public BalanceMerchantLogDomain findByKey(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.findByKey(t);
	}

	@Override
	public List<BalanceMerchantLogDomain> findByWhere(BalanceMerchantLogDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.findByWhere(t);
	}

	@Override
	public String getCountBalanceByIn(BalanceMerchantLogDomain balanceMerchantLogDomain) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.getCountBalanceByIn(balanceMerchantLogDomain);
	}

	@Override
	public List<BalanceMerchantLogDomain> getBalanceLogByUser(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return balanceMerchantLogDAO.getBalanceLogByUser(userDomain);
	}

}
