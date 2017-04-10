package sds.webapp.stm.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.excel.ExcelUtils;
import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
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
import sds.webapp.stm.util.SettlementHandler;

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
	 * 根据代理商查询订单分润
	 * 
	 * @param profitDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=getProfitByUser")
	public String getProfitByUser(ProfitDomain profitDomain) {
		profitDomain.setAgentId(UserUtils.getPrincipal().getUserDomain().getId());
		List<ProfitDomain> list = profitService.getProfitByUser(profitDomain);
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
	 * 分润统计--公共方法，方便调用 map-user-profitUserDomains
	 * map-merchant-profitMerchantDomains
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, List> getProfitCountMap(List<ProfitDomain> list) {

		Map<Integer, ProfitUserDomain> profitUserMap = new HashMap<Integer, ProfitUserDomain>();
		Map<Integer, ProfitMerchantDomain> profitMerchantMap = new HashMap<Integer, ProfitMerchantDomain>();

		list.stream().forEach((profit) -> {

			ProfitUserDomain profitUserDomain = profitUserMap.get(profit.getAgentId());

			if (profitUserDomain == null) {
				profitUserDomain = new ProfitUserDomain();
				profitUserDomain.setAgentId(profit.getAgentId());
				profitUserMap.put(profit.getAgentId(), profitUserDomain);
			}

			profitUserDomain
					.setTotalAmount(SettlementHandler.sum(profitUserDomain.getTotalAmount(), profit.getAmount()));
			profitUserDomain
					.setTotalProfit(SettlementHandler.sum(profitUserDomain.getTotalProfit(), profit.getAgentProfit()));
			profitUserDomain.setDate(profit.getOrderDate());
			profitUserDomain.setStatus(0);

			if (profit.getTjId() != null) {
				// 存在推荐人
				ProfitMerchantDomain profitMerchantDomain = profitMerchantMap.get(profit.getTjId());
				if (profitMerchantDomain == null) {
					profitMerchantDomain = new ProfitMerchantDomain();
					profitMerchantDomain.setMerchantId(profit.getTjId());
					profitMerchantMap.put(profit.getTjId(), profitMerchantDomain);
				}
				profitMerchantDomain.setTotalAmount(
						SettlementHandler.sum(profitMerchantDomain.getTotalAmount(), profit.getAmount()));
				profitMerchantDomain.setTotalProfit(
						SettlementHandler.sum(profitMerchantDomain.getTotalProfit(), profit.getTjProfit()));
				profitMerchantDomain.setDate(profit.getOrderDate());
				profitMerchantDomain.setStatus(0);
			}
		});

		List<ProfitUserDomain> profitUserDomains = new ArrayList<>(profitUserMap.values());
		List<ProfitMerchantDomain> profitMerchantDomains = new ArrayList<>(profitMerchantMap.values());

		SettlementHandler.realtimeComputationBalance(list);

		Map<String, List> map = new HashMap<String, List>();
		map.put("user", profitUserDomains);
		map.put("merchant", profitMerchantDomains);
		return map;
	}

	/**
	 * 分润统计（目前用于定时任务，只计算指定日期内status=1的分润）
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
		profitDomain.setStatus(1);
		List<ProfitDomain> list = profitService.getAllProfit(profitDomain);
		Map<String, List> map = getProfitCountMap(list);
		profitService.profitCount(map.get("user"), map.get("merchant"), list);

		// Map<Integer, ProfitUserDomain> profitUserMap = new HashMap<Integer,
		// ProfitUserDomain>();
		// Map<Integer, ProfitMerchantDomain> profitMerchantMap = new
		// HashMap<Integer, ProfitMerchantDomain>();
		//
		// list.stream().forEach((profit) -> {
		//
		// ProfitUserDomain profitUserDomain =
		// profitUserMap.get(profit.getAgentId());
		//
		// if (profitUserDomain == null) {
		// profitUserDomain = new ProfitUserDomain();
		// profitUserDomain.setAgentId(profit.getAgentId());
		// profitUserMap.put(profit.getAgentId(), profitUserDomain);
		// }
		//
		// profitUserDomain.setTotalAmount(SettlementUtil.sum(profitUserDomain.getTotalAmount(),
		// profit.getAmount()));
		// profitUserDomain
		// .setTotalProfit(SettlementUtil.sum(profitUserDomain.getTotalProfit(),
		// profit.getAgentProfit()));
		// profitUserDomain.setDate(profitDomain.getOrderDate());
		// profitUserDomain.setStatus(0);
		//
		// if (profit.getTjId() != null) {
		// // 存在推荐人
		// ProfitMerchantDomain profitMerchantDomain =
		// profitMerchantMap.get(profit.getTjId());
		// if (profitMerchantDomain == null) {
		// profitMerchantDomain = new ProfitMerchantDomain();
		// profitMerchantDomain.setMerchantId(profit.getTjId());
		// profitMerchantMap.put(profit.getTjId(), profitMerchantDomain);
		// }
		// profitMerchantDomain
		// .setTotalAmount(SettlementUtil.sum(profitMerchantDomain.getTotalAmount(),
		// profit.getAmount()));
		// profitMerchantDomain.setTotalProfit(
		// SettlementUtil.sum(profitMerchantDomain.getTotalProfit(),
		// profit.getTjProfit()));
		// profitMerchantDomain.setDate(profitDomain.getOrderDate());
		// profitMerchantDomain.setStatus(0);
		// }
		// });
		//
		// List<ProfitUserDomain> profitUserDomains = new
		// ArrayList<>(profitUserMap.values());
		// List<ProfitMerchantDomain> profitMerchantDomains = new
		// ArrayList<>(profitMerchantMap.values());
		// profitService.profitCount(profitUserDomains, profitMerchantDomains,
		// list);

		return DateUtil.formatDate(profitDomain.getOrderDate());
	}

	/**
	 * 重算（以订单为单位）(购卡订单无法重算，屏蔽)
	 */
	@ResponseBody
	@RequestMapping(params = "type=recalculation")
	public String recalculation(OrderDomain orderDomain) {
		// 获取需要重算的订单，可重算一个（指定订单号）或批量（时间段内的订单）冲算
		orderDomain.setStatus(3);// 指定订单类型，只重算已经分润的订单。
		List<OrderDomain> orderDomains = orderService.findByWhere(orderDomain);

		Iterator<OrderDomain> iterator = orderDomains.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getChannelCode() == 3) {
				iterator.remove();
			}
		}

		Map<String, MARDomain> marMap = getMAR();
		List<ProfitDomain> list = call(orderDomains, marMap);
		SettlementHandler.recalculationComputationBalance(list);// 重算余额
		// 更新
		int i = profitService.recalculation(list);
		if (i != 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "重算成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "重算失败."));
		}
	}

	/**
	 * 计算分润（非正常方法）
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
		SettlementHandler.realtimeComputationBalance(list);// 计算余额
		// 批量插入
		int i = profitService.profit(list, orderDomains);
		// System.out.println(i);
		return "完成分润" + i;
	}
	//下级分润表格导出
	@ResponseBody
	@RequestMapping(params = "type=exportExcel")
	public String exportExcel(ProfitUserDomain profitUserDomain, HttpServletResponse httpServletResponse)
			throws IOException {

		// ProfitUserDomain profitUserDomain = new ProfitUserDomain();
		profitUserDomain.setAgentId(UserUtils.getPrincipal().getUserDomain().getId());

		List<ProfitDomain> list = profitService.findSubProfitByUser(profitUserDomain);

		List<ProfitUserDomain> profitUserDomains = SettlementHandler.computeProfitByUser(list);

		for (ProfitUserDomain temp : profitUserDomains) {

			UserDomain userDomain = new UserDomain();
			userDomain.setAccount(temp.getAccount());
			userDomain = userService.findByKey(userDomain);

			// 法人姓名
			temp.setRegName(userDomain.getRegName());
			// 结算卡
			temp.setJsCard(userDomain.getJsCard());

			temp.setJsAddress(userDomain.getJsAddress());

			temp.setJsBank(userDomain.getJsBank());
			temp.setJsBankadd(userDomain.getJsBankadd());
			temp.setJsName(userDomain.getJsName());
			temp.setJsLhno(userDomain.getJsLhno());

		}

		ExcelUtils.export(profitUserDomains, httpServletResponse);

		return null;
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
			// 扫码分润
			List<ProfitDomain> list = SettlementHandler.getScanInstance().createProfit(marMap.get(order.getAccount()),
					order);
			result.addAll(list);
		});
		return result;
	}

}
