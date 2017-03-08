package sds.webapp.stm.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.stm.domain.ProfitMerchantDomain;

public interface ProfitMerchantService extends BaseService<ProfitMerchantDomain> {
	public int insertBatch(List<ProfitMerchantDomain> list);

	public String getMerchantTotalProfit(ProfitMerchantDomain profitMerchantDomain);
}
