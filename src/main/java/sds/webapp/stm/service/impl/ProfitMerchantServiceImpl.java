package sds.webapp.stm.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.stm.dao.ProfitMerchantDAO;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.service.ProfitMerchantService;

@TransactionService
public class ProfitMerchantServiceImpl implements ProfitMerchantService {

	@TransactionDAO
	private ProfitMerchantDAO profitMerchantDAO;

	@Override
	public int insert(ProfitMerchantDomain t) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.insert(t);
	}

	@Override
	public int delete(ProfitMerchantDomain t) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.delete(t);
	}

	@Override
	public int update(ProfitMerchantDomain t) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.update(t);
	}

	@Override
	public ProfitMerchantDomain findByKey(ProfitMerchantDomain t) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.findByKey(t);
	}

	@Override
	public List<ProfitMerchantDomain> findByWhere(ProfitMerchantDomain t) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.findByWhere(t);
	}

	@Override
	public int insertBatch(List<ProfitMerchantDomain> list) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.insertBatch(list);
	}

	@Override
	public String getMerchantTotalProfit(ProfitMerchantDomain profitMerchantDomain) {
		// TODO Auto-generated method stub
		return profitMerchantDAO.getMerchantTotalProfit(profitMerchantDomain);
	}

}
