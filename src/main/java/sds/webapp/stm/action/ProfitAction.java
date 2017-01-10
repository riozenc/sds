package sds.webapp.stm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
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
@ControllerAdvice
@RequestMapping("profit")
public class ProfitAction extends BaseAction {

	@Autowired
	@Qualifier("profitServiceImpl")
	private ProfitService profitService;
	@Autowired
	@Qualifier("orderServiceImpl")
	private OrderService orderService;
	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@ResponseBody
	@RequestMapping(params = "type=getProfit")
	public String getProfit(ProfitDomain profitDomain) {

		List<ProfitDomain> list = profitService.findByWhere(profitDomain);

		return JSONUtil.toJsonString(new JsonGrid(list.size(), 1, list));
	}

	@RequestMapping(params = "type=profit")
	public String profit() {
		OrderDomain orderDomain = new OrderDomain();
		orderDomain.setStatus(1);
		// 获取指定时间的所有交易订单
		List<OrderDomain> orderDomains = orderService.findByWhere(orderDomain);
		// 获取商户代理商关系
		Map<String, MARDomain> marMap = getMAR();

		List<ProfitDomain> list = call(orderDomains, marMap);

		// 批量插入
		int i = profitService.insertBatch(list);
		// System.out.println(i);
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

			MARDomain marDomain = new MARDomain(temp, list);
			marMap.put(temp.getAccount(), marDomain);
		}
		return marMap;
	}

	private List<ProfitDomain> call(List<OrderDomain> orderDomains, Map<String, MARDomain> marMap) {
		List<ProfitDomain> result = new ArrayList<>();

		orderDomains.stream().forEach((order) -> {

			List<ProfitDomain> list = SettlementUtil.createProfit(marMap.get(order.getAccount()), order);

			result.addAll(list);

		});
		return result;
	}

}
