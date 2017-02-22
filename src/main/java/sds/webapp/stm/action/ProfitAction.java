package sds.webapp.stm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.security.Principal;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.acc.service.UserService;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.service.ProfitMerchantService;
import sds.webapp.stm.service.ProfitService;
import sds.webapp.stm.service.ProfitUserService;
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

	@Autowired
	@Qualifier("profitUserServiceImpl")
	private ProfitUserService profitUserService;//
	@Autowired
	@Qualifier("profitMerchantServiceImpl")
	private ProfitMerchantService profitMerchantService;//

	/**
	 * 获取分润明细
	 * 
	 * @param profitDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=getProfit")
	public String getProfit(ProfitDomain profitDomain) {
		List<ProfitDomain> list = profitService.findByWhere(profitDomain);
		return JSONUtil.toJsonString(new JsonGrid(list.size(), 1, list));
	}

	/**
	 * 获取商户的佣金明细
	 * 
	 * @param profitDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=getProfitMerchant")
	public String getProfitMerchant(ProfitDomain profitDomain) {
		List<ProfitDomain> list = profitService.findByWhere(profitDomain);
		return JSONUtil.toJsonString(new JsonGrid(list.size(), 1, list));
	}

	/**
	 * 分润统计
	 * 
	 * @param profitDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=profitCount")
	public String profitCount(ProfitDomain profitDomain) {
		if (profitDomain.getOrderDate() == null) {
			profitDomain.setOrderDate(new Date());
		}
		List<ProfitDomain> list = profitService.getAllProfit(profitDomain);

		// List<ProfitUserDomain> profitUserDomains = new
		// ArrayList<ProfitUserDomain>();
		// List<ProfitMerchantDomain> profitMerchantDomains = new
		// ArrayList<ProfitMerchantDomain>();

		Map<Integer, ProfitUserDomain> profitUserMap = new HashMap<Integer, ProfitUserDomain>();
		Map<Integer, ProfitMerchantDomain> profitMerchantMap = new HashMap<Integer, ProfitMerchantDomain>();

		list.stream().forEach((profit) -> {

			ProfitUserDomain profitUserDomain = profitUserMap.get(profit.getAgentId());

			if (profitUserDomain == null) {
				profitUserDomain = new ProfitUserDomain();
				profitUserDomain.setAgentId(profit.getAgentId());
				profitUserMap.put(profit.getAgentId(), profitUserDomain);
			}

			profitUserDomain.setTotalAmount(SettlementUtil.sum(profitUserDomain.getTotalAmount(), profit.getAmount()));
			profitUserDomain
					.setTotalProfit(SettlementUtil.sum(profitUserDomain.getTotalProfit(), profit.getAgentProfit()));
			profitUserDomain.setDate(profitDomain.getOrderDate());
			profitUserDomain.setStatus(0);

			if (profit.getTjId() != null) {
				// 存在推荐人
				ProfitMerchantDomain profitMerchantDomain = profitMerchantMap.get(profit.getTjId());
				if (profitMerchantDomain == null) {
					profitMerchantDomain = new ProfitMerchantDomain();
					profitMerchantDomain.setMerchantId(profit.getTjId());
					profitMerchantMap.put(profit.getTjId(), profitMerchantDomain);
				}
				profitMerchantDomain
						.setTotalAmount(SettlementUtil.sum(profitMerchantDomain.getTotalAmount(), profit.getAmount()));
				profitMerchantDomain.setTotalProfit(
						SettlementUtil.sum(profitMerchantDomain.getTotalProfit(), profit.getTjProfit()));
				profitMerchantDomain.setDate(profitDomain.getOrderDate());
				profitMerchantDomain.setStatus(0);
			}
		});

		List<ProfitUserDomain> profitUserDomains = new ArrayList<>(profitUserMap.values());
		List<ProfitMerchantDomain> profitMerchantDomains = new ArrayList<>(profitMerchantMap.values());

		profitService.profitCount(profitUserDomains, profitMerchantDomains, list);

		return DateUtil.formatDate(profitDomain.getOrderDate());
	}

	/**
	 * 计算分润
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=profit")
	public String profit() throws Exception {

		OrderDomain orderDomain = new OrderDomain();
		// 获取指定时间的所有交易订单
		List<OrderDomain> orderDomains = orderService.getAllCheckedOrder(orderDomain);
		// 获取商户代理商关系
		Map<String, MARDomain> marMap = getMAR();
		List<ProfitDomain> list = call(orderDomains, marMap);

		// 批量插入
		int i = profitService.profit(list, orderDomains);
		// System.out.println(i);
		return "完成分润" + i;
	}

	/**
	 * 根据商户查询分润//有问题
	 * 
	 * @param userDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findProfitByUser")
	public String findProfitByUser(ProfitUserDomain profitUserDomain) {

		List<ProfitDomain> list = profitService.findProfitByUser(profitUserDomain);

		return JSONUtil.toJsonString(new JsonGrid(profitUserDomain, list));
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

	private List<ProfitDomain> call(List<OrderDomain> orderDomains, Map<String, MARDomain> marMap) {
		List<ProfitDomain> result = new ArrayList<>();

		orderDomains.stream().forEach((order) -> {

			List<ProfitDomain> list = SettlementUtil.createProfit(marMap.get(order.getAccount()), order);

			result.addAll(list);

		});
		return result;
	}

}
