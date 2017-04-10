/**
 * Title:WithdrawalsServiceImpl.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:28:21
**/
package sds.webapp.stm.service.impl;

import java.util.Date;
import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.stm.dao.ProfitMerchantDAO;
import sds.webapp.stm.dao.WithdrawalsDAO;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.domain.WithdrawalsDomain;
import sds.webapp.stm.service.WithdrawalsService;
import sds.webapp.stm.util.SettlementHandler;

@TransactionService
public class WithdrawalsServiceImpl implements WithdrawalsService {

	@TransactionDAO
	private WithdrawalsDAO withdrawalsDAO;

	@TransactionDAO
	private ProfitMerchantDAO profitMerchantDAO;

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

	@Override
	public int agree(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		ProfitMerchantDomain profitMerchantDomain = new ProfitMerchantDomain();
		profitMerchantDomain.setMerchantId(withdrawalsDomain.getMerchantId());
		profitMerchantDomain.setTotalAmount(0d);
		profitMerchantDomain.setTotalProfit(-withdrawalsDomain.getAmount());
		profitMerchantDomain.setDate(new Date());
		profitMerchantDomain.setStatus(1);

		withdrawalsDomain.setStatus(1);// 成功

		SettlementHandler.realtimeComputationBalance(withdrawalsDomain);
		return withdrawalsDAO.update(withdrawalsDomain) + profitMerchantDAO.insert(profitMerchantDomain);

	}

	@Override
	public List<WithdrawalsDomain> getwithdrawals(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		return withdrawalsDAO.getwithdrawals(withdrawalsDomain);
	}

	@Override
	//这个方法用来导出提现金额明细
	public List<WithdrawalsDomain> findAmountExcel(WithdrawalsDomain withdrawalsDomain) {
		// TODO Auto-generated method stub
		 return withdrawalsDAO.findAmountExcel(withdrawalsDomain);
	}

}
