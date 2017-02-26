package sds.webapp.stm.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.dao.MerchantDAO;
import sds.webapp.acc.dao.UserDAO;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.dao.OrderDAO;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.dao.ProfitDAO;
import sds.webapp.stm.dao.ProfitMerchantDAO;
import sds.webapp.stm.dao.ProfitUserDAO;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.service.ProfitService;
import sds.webapp.stm.util.SettlementUtil;

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
	@TransactionDAO
	private UserDAO userDAO;
	@TransactionDAO
	private MerchantDAO merchantDAO;

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
		int sum=0;
		if(!profitUserDomains.isEmpty()){			
			sum+=profitUserDAO.insertBatch(profitUserDomains);
		}
		if(!profitMerchantDomains.isEmpty()){			
			sum+=profitMerchantDAO.insertBatch(profitMerchantDomains);
		}
		if(!profitDomains.isEmpty()){
			sum+=profitDAO.profitCountComplete(profitDomains);
		}
		return sum;
	}

	@Override
	public List<ProfitDomain> findProfitByUser(ProfitUserDomain profitUserDomain) {
		// TODO Auto-generated method stub
		return profitDAO.findProfitByUser(profitUserDomain);
	}

	@Override
	public int profit(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		Map<String, MARDomain> marMap = getMAR();
		List<ProfitDomain> list = call(orderDomain, marMap);
		return profitDAO.insertBatch(list);
	}

	/**
	 * 获取代理商商户关系
	 * 
	 * @param merchantDomain
	 */
	private Map<String, MARDomain> getMAR() {
		Map<String, MARDomain> marMap = new HashMap<String, MARDomain>();
		List<UserDomain> users = userDAO.getAllCheckedUser();
		Map<Integer, UserDomain> userMap = new HashMap<Integer, UserDomain>();
		for (UserDomain temp : users) {
			userMap.put(temp.getId(), temp);
		}
		// 获取审核过的商户
		List<MerchantDomain> merchants = merchantDAO.getAllCheckedMerchant();
		Map<Integer, MerchantDomain> merchantMap = new HashMap<Integer, MerchantDomain>();
		for (MerchantDomain temp : merchants) {
			merchantMap.put(temp.getId(), temp);
			if (temp.getTjId() != null) {
				merchantMap.get(temp.getTjId());
			}
			LinkedList<UserDomain> list = new LinkedList<>();
			list.add(userMap.get(temp.getAgentId()));
			while (true) {
				if (list.getLast().getParentId() != 0) {
					list.addLast(userMap.get(list.getLast().getParentId()));
				} else {
					break;
				}
			}
			MARDomain marDomain = new MARDomain(temp, list);
			marMap.put(temp.getAccount(), marDomain);
		}
		return marMap;
	}

	private List<ProfitDomain> call(OrderDomain orderDomain, Map<String, MARDomain> marMap) {
		List<ProfitDomain> list = SettlementUtil.createProfit(marMap.get(orderDomain.getAccount()), orderDomain);
		return list;
	}

}
