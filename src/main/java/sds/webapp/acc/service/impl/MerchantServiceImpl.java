package sds.webapp.acc.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.dao.MerchantDAO;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.blc.dao.BalanceMerchantDAO;
import sds.webapp.blc.domain.BalanceMerchantDomain;

@TransactionService
public class MerchantServiceImpl implements MerchantService {

	@TransactionDAO
	private MerchantDAO merchantDAO;
	@TransactionDAO
	private BalanceMerchantDAO balanceMerchantDAO;

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

	/**
	 * 融入validCard方法中，暂时未使用
	 */
	@Override
	public int updateRV(MerchantDomain real, MerchantDomain virtual) {
		// TODO Auto-generated method stub
		merchantDAO.update(real);
		return merchantDAO.updatePool(virtual);
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
	public List<MerchantDomain> findMerchantByUser(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		return merchantDAO.findMerchantByUser(merchantDomain);
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
	public MerchantDomain getVirtualMerchantByKey(String account) {
		return merchantDAO.getVirtualMerchantByKey(account);
	}

	@Override
	public Map<String, String> getRAandVP(String account) {
		return merchantDAO.getRAandVP(account);
	}

	@Override
	public void validCard(MerchantDomain real, MerchantDomain virtual) {
		merchantDAO.relievePoolRel(real.getId());

		Map<String, Integer> map = new HashMap<>();
		map.put("merchantId", real.getId());
		map.put("poolId", virtual.getId());
		map.put("status", 1);
		merchantDAO.insertPoolRel(map);

		merchantDAO.update(real);
		merchantDAO.updatePool(virtual);
	}

	@Override
	public int register(MerchantDomain merchantDomain) {
		// TODO Auto-generated method stub
		
		insert(merchantDomain);
		BalanceMerchantDomain balanceMerchantDomain = new BalanceMerchantDomain();
		balanceMerchantDomain.setAccount(merchantDomain.getAccount());
		balanceMerchantDomain.setBalance(new BigDecimal(0));
		balanceMerchantDomain.setCountIn(new BigDecimal(0));
		balanceMerchantDomain.setCountOut(new BigDecimal(0));
		balanceMerchantDomain.setCreateDate(new Date());
		balanceMerchantDomain.setTargetId(merchantDomain.getId());

		return balanceMerchantDAO.insert(balanceMerchantDomain);
	}

}
