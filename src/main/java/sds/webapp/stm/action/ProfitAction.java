package sds.webapp.stm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.acc.service.UserService;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.service.ProfitService;
import sds.webapp.stm.util.SettlementUtil;

/**
 * 分润
 * 
 * @author riozenc
 *
 */
public class ProfitAction extends BaseAction {

	private ProfitService profitService;
	private OrderService orderService;
	private MerchantService merchantService;
	private UserService userService;

	public String profit() {
		OrderDomain orderDomain = new OrderDomain();
		// 获取指定时间的所有交易订单
		List<OrderDomain> orderDomains = orderService.findByWhere(orderDomain);
		// 获取商户代理商关系
		Map<String, MARDomain> marMap = getMAR();

		List<ProfitDomain> list = call(orderDomains, marMap);

		// 批量插入

		return null;
	}

	/**
	 * 获取代理商商户关系
	 * 
	 * @param merchantDomain
	 */
	private Map<String, MARDomain> getMAR() {
		Map<String, MARDomain> marMap = new HashMap<String, MARDomain>();
		List<UserDomain> users = userService.getAllCheckedUser();
		Map<Integer, UserDomain> userMap = new HashMap<Integer, UserDomain>();
		for (UserDomain temp : users) {
			userMap.put(temp.getId(), temp);
		}
		// 获取审核过的商户
		List<MerchantDomain> merchants = merchantService.getAllCheckedMerchant();
		for (MerchantDomain temp : merchants) {
			LinkedList<UserDomain> list = new LinkedList<>();
			list.add(userMap.get(temp.getAgentId()));
			while (true) {
				if (list.getLast().getParentId() != 0) {
					list.addLast(userMap.get(list.getLast().getParentId()));
				} else {
					break;
				}
			}

			MARDomain MARDomain = new MARDomain(temp, list);
			marMap.put(temp.getAccount(), MARDomain);
		}
		return marMap;
	}

	private List<ProfitDomain> call(List<OrderDomain> orderDomains, Map<String, MARDomain> marMap) {
		List<ProfitDomain> result = new ArrayList<>();

		orderDomains.stream().forEach(order -> {
			List<ProfitDomain> list = SettlementUtil.createProfit(marMap.get(order.getAccount()), order);
			checkProfit(list);
			result.addAll(list);
		});
		return result;
	}

	private void checkProfit(List<ProfitDomain> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			if ((i + 2) == list.size()) {
				if (sum != list.get(i).getAgentProfit()) {
					list.get(i).setAgentProfit(list.get(i).getTotalProfit() - sum);
				}
			}
			sum += list.get(i).getAgentProfit();
		}
	}

}
