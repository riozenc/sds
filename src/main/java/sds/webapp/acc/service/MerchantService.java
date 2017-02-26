package sds.webapp.acc.service;

import java.util.List;
import java.util.Map;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.MerchantDomain;

public interface MerchantService extends BaseService<MerchantDomain> {
	public int updateRate(MerchantDomain merchantDomain);

	public List<MerchantDomain> getAllCheckedMerchant();

	public MerchantDomain getUser(MerchantDomain merchantDomain);

	public Map<String, Object> checkRate(int id);

	public int updateRV(MerchantDomain real, MerchantDomain virtual);

	public int updatePool(MerchantDomain merchantDomain);

	public int updatePoolRel(Integer merchantId, Integer poolId);

	public int insertPoolRel(Integer merchantId, Integer poolId);

	public List<MerchantDomain> findMerchantByUser(String account);

	public List<MerchantDomain> getVirtualMerchants(MerchantDomain merchantDomain);

	public MerchantDomain getVirtualMerchant(MerchantDomain merchantDomain);
	public void validCard(MerchantDomain real, MerchantDomain virtual);
}
