package sds.webapp.stm.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.domain.ProfitUserDomain;

public interface ProfitService extends BaseService<ProfitDomain> {

	public List<ProfitDomain> getAllProfit(ProfitDomain profitDomain);

	public int profit(OrderDomain orderDomain);

	public int profitQuick(OrderDomain orderDomain);

	public int profit(List<ProfitDomain> list, List<OrderDomain> orderDomains);

	public int profitCount(List<ProfitUserDomain> profitUserDomains, List<ProfitMerchantDomain> profitMerchantDomains,
			List<ProfitDomain> profitDomains);

	public List<ProfitDomain> findProfitByUser(ProfitUserDomain profitUserDomain);

	public List<ProfitDomain> findSubProfitByUser(ProfitUserDomain profitUserDomain);

	public List<ProfitUserDomain> findDateProfitUserByWhere(ProfitUserDomain profitUserDomain);

	public int recalculation(List<ProfitDomain> list);

	public List<ProfitDomain> getProfitByUser(ProfitDomain profitDomain);

}
