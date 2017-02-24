package sds.webapp.acc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.dao.MerchantDAO;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.service.MerchantService;

@TransactionService
public class MerchantServiceImpl implements MerchantService {

	@TransactionDAO
	private MerchantDAO merchantDAO;

	@Override
	public int insert(MerchantDomain t) {
		// TODO Auto-generated method stub

		t.setCreateDate(new Date());
		t.setStatus(0);

		return merchantDAO.insert(t);
	}

	@Override
	public int delete(MerchantDomain t) {
		// TODO Auto-generated method stub
		return merchantDAO.delete(t);
	}

	@Override
	public int update(MerchantDomain t) {
		// TODO Auto-generated method stub
		return merchantDAO.update(t);
	}

	@Override
	public MerchantDomain findByKey(MerchantDomain t) {
		// TODO Auto-generated method stub
		return merchantDAO.findByKey(t);
	}

	@Override
	public List<MerchantDomain> findByWhere(MerchantDomain t) {
		// TODO Auto-generated method stub
		return merchantDAO.findByWhere(t);
	}

	@Override
	public int updateRate(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		return merchantDAO.updateRate(merchantDomain);
	}

	@Override
	public List<MerchantDomain> getAllCheckedMerchant() {
		// TODO Auto-generated method stub
		return merchantDAO.getAllCheckedMerchant();
	}

	@Override
	public MerchantDomain getUser(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		return merchantDAO.getUser(merchantDomain);
	}

	@Override
	public Map<String, Object> checkRate(int id) {
		// TODO Auto-generated method stub

		return merchantDAO.checkRate(id);
	}

	@Override
	public int updatePoolRel(Integer merchantId, Integer poolId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		map.put("merchantId", merchantId);
		map.put("poolId", poolId);
		return merchantDAO.updatePoolRel(map);
	}

	@Override
	public int insertPoolRel(Integer merchantId, Integer poolId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		map.put("merchantId", merchantId);
		map.put("poolId", poolId);
		map.put("status", 1);
		return merchantDAO.insertPoolRel(map);
	}

	@Override
	public int updatePool(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		return merchantDAO.updatePool(merchantDomain);
	}

	@Override
	public List<MerchantDomain> getVirtualMerchants(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		return merchantDAO.getVirtualMerchants(merchantDomain);
	}

	@Override
	public MerchantDomain getVirtualMerchant(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		return merchantDAO.getVirtualMerchant(merchantDomain);
	}

	@Override
	public List<MerchantDomain> findMerchantByUser(String account) {
		// TODO Auto-generated method stub
		return merchantDAO.findMerchantByUser(account);
	}

}
