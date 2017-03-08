/**
 * Title:BalanceMerchantServiceImpl.java
 * Author:riozenc
 * Datetime:2017年3月7日 下午3:45:03
**/
package sds.webapp.blc.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.blc.dao.BalanceMerchantDAO;
import sds.webapp.blc.domain.BalanceMerchantDomain;
import sds.webapp.blc.service.BalanceMerchantService;

@TransactionService
public class BalanceMerchantServiceImpl implements BalanceMerchantService {

	@TransactionDAO
	private BalanceMerchantDAO balanceMerchantDAO;

	@Override
	public int insert(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantDAO.insert(t);
	}

	@Override
	public int delete(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantDAO.delete(t);
	}

	@Override
	public int update(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantDAO.update(t);
	}

	@Override
	public BalanceMerchantDomain findByKey(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantDAO.findByKey(t);
	}

	@Override
	public List<BalanceMerchantDomain> findByWhere(BalanceMerchantDomain t) {
		// TODO Auto-generated method stub
		return balanceMerchantDAO.findByWhere(t);
	}

}
