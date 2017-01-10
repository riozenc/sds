package sds.webapp.acc.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.MerchantDomain;

public interface MerchantService extends BaseService<MerchantDomain> {
	public int updateRate(MerchantDomain merchantDomain);

	public List<MerchantDomain> getAllCheckedMerchant();
	
	public MerchantDomain getUser(MerchantDomain merchantDomain);

}
