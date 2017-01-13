package sds.webapp.acc.service;

import java.util.List;
import java.util.Map;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.MerchantDomain;

public interface MerchantService extends BaseService<MerchantDomain> {
	public int updateRate(MerchantDomain merchantDomain);

	public List<MerchantDomain> getAllCheckedMerchant();
	
	public MerchantDomain getUser(MerchantDomain merchantDomain);
	
	public Map<String,Object> checkRate(int id);

}
