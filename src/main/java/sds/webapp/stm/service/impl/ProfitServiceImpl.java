package sds.webapp.stm.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.dao.OrderDAO;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.dao.ProfitDAO;
import sds.webapp.stm.dao.ProfitMerchantDAO;
import sds.webapp.stm.dao.ProfitUserDAO;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.service.ProfitService;

@TransactionService
public class ProfitServiceImpl implements ProfitService {

	@TransactionDAO
	private ProfitDAO profitDAO;
	@TransactionDAO
	private ProfitUserDAO profitUserDAO;
	@TransactionDAO
	private ProfitMerchantDAO profitMerchantDAO;
	@TransactionDAO
	private OrderDAO orderDAO;

	@Override
	public int insert(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.insert(t);
	}

	@Override
	public int delete(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.delete(t);
	}

	@Override
	public int update(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.update(t);
	}

	@Override
	public ProfitDomain findByKey(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.findByKey(t);
	}

	@Override
	public List<ProfitDomain> findByWhere(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.findByWhere(t);
	}

	@Override
	public int profit(List<ProfitDomain> list, List<OrderDomain> orderDomains) {
		// TODO Auto-generated method stub

		profitDAO.insertBatch(list);
		return orderDAO.profitComplete(orderDomains);
	}

	@Override
	public List<ProfitDomain> getAllProfit(ProfitDomain profitDomain) {
		// TODO Auto-generated method stub
		return profitDAO.getAllProfit(profitDomain);
	}

	@Override
	public int profitCount(List<ProfitUserDomain> profitUserDomains, List<ProfitMerchantDomain> profitMerchantDomains,
			List<ProfitDomain> profitDomains) {
		// TODO Auto-generated method stub
		profitUserDAO.insertBatch(profitUserDomains);
		profitMerchantDAO.insertBatch(profitMerchantDomains);
		return profitDAO.profitCountComplete(profitDomains);
	}

	@Override
	public List<ProfitDomain> findProfitByUser(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return profitDAO.findProfitByUser(userDomain);
	}

}
