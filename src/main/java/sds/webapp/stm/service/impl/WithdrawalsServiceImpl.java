/**
 * Title:WithdrawalsServiceImpl.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:28:21
**/
package sds.webapp.stm.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.stm.dao.WithdrawalsDAO;
import sds.webapp.stm.domain.WithdrawalsDomain;
import sds.webapp.stm.service.WithdrawalsService;

@TransactionService
public class WithdrawalsServiceImpl implements WithdrawalsService {

	@TransactionDAO
	private WithdrawalsDAO withdrawalsDAO;

	@Override
	public int insert(WithdrawalsDomain t) {
		// TODO Auto-generated method stub
		return withdrawalsDAO.insert(t);
	}

	@Override
	public int delete(WithdrawalsDomain t) {
		// TODO Auto-generated method stub
		return withdrawalsDAO.delete(t);
	}

	@Override
	public int update(WithdrawalsDomain t) {
		// TODO Auto-generated method stub
		return withdrawalsDAO.update(t);
	}

	@Override
	public WithdrawalsDomain findByKey(WithdrawalsDomain t) {
		// TODO Auto-generated method stub
		return withdrawalsDAO.findByKey(t);
	}

	@Override
	public List<WithdrawalsDomain> findByWhere(WithdrawalsDomain t) {
		// TODO Auto-generated method stub
		return withdrawalsDAO.findByWhere(t);
	}
}
