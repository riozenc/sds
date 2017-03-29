package sds.webapp.stm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sds.common.queue.entity.BalanceEntity;
import sds.common.queue.manager.QueueManager;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.domain.WithdrawalsDomain;

/**
 * 结算工具类
 * 
 * @author riozenc
 *
 */
public class SettlementUtil {

//	private final static int DEFAULT_PAY = 1;// 微信
//
//	/**
//	 * 根据分润明细计算商户佣金
//	 * 
//	 * @param list
//	 *            分润明细list
//	 * @return
//	 */
//	public static List<ProfitMerchantDomain> computeProfitByMerchant(List<ProfitDomain> list) {
//		Map<Integer, ProfitMerchantDomain> profitMerchantMap = new HashMap<Integer, ProfitMerchantDomain>();
//
//		list.stream().forEach((profit) -> {
//			if (profit.getTjId() != null) {
//				// 存在推荐人
//				ProfitMerchantDomain profitMerchantDomain = profitMerchantMap.get(profit.getTjId());
//				if (profitMerchantDomain == null) {
//					profitMerchantDomain = new ProfitMerchantDomain();
//					profitMerchantDomain.setMerchantId(profit.getTjId());
//					profitMerchantMap.put(profit.getTjId(), profitMerchantDomain);
//				}
//				profitMerchantDomain
//						.setTotalAmount(SettlementUtil.sum(profitMerchantDomain.getTotalAmount(), profit.getAmount()));
//				profitMerchantDomain.setTotalProfit(
//						SettlementUtil.sum(profitMerchantDomain.getTotalProfit(), profit.getTjProfit()));
//				profitMerchantDomain.setDate(profit.getOrderDate());
//				profitMerchantDomain.setStatus(0);
//			}
//		});
//		List<ProfitMerchantDomain> profitMerchantDomains = new ArrayList<>(profitMerchantMap.values());
//		return profitMerchantDomains;
//	}
//
//	/**
//	 * 根据分润明细计算代理商分润
//	 * 
//	 * @param list
//	 *            分润明细list
//	 * @return
//	 */
//	public static List<ProfitUserDomain> computeProfitByUser(List<ProfitDomain> list) {
//		Map<Integer, ProfitUserDomain> profitUserMap = new HashMap<Integer, ProfitUserDomain>();
//		list.stream().forEach((profit) -> {
//
//			ProfitUserDomain profitUserDomain = profitUserMap.get(profit.getAgentId());
//
//			if (profitUserDomain == null) {
//				profitUserDomain = new ProfitUserDomain();
//				profitUserDomain.setAgentId(profit.getAgentId());
//				profitUserMap.put(profit.getAgentId(), profitUserDomain);
//			}
//			profitUserDomain.setAccount(profit.getAgentAccount());
//			profitUserDomain.setTotalAmount(SettlementUtil.sum(profitUserDomain.getTotalAmount(), profit.getAmount()));
//			profitUserDomain
//					.setTotalProfit(SettlementUtil.sum(profitUserDomain.getTotalProfit(), profit.getAgentProfit()));
//			profitUserDomain.setDate(profit.getOrderDate());
//			profitUserDomain.setStatus(0);
//
//		});
//
//		List<ProfitUserDomain> profitUserDomains = new ArrayList<>(profitUserMap.values());
//		return profitUserDomains;
//	}
//
//	// 对订单进行分润（扫码）
//	public static List<ProfitDomain> createProfit(MARDomain mar, OrderDomain order) {
//		List<ProfitDomain> list = new ArrayList<ProfitDomain>();
//		LinkedList<UserDomain> agents = new LinkedList<>(mar.getAgents());
//		int size = mar.getAgents().size();
//		if (size == 0) {
//			return null;
//		}
//		// 商户代理分润
//		list.add(buildStmDomain(order, mar.getMerchant(), agents.getFirst()));
//
//		// 代理分润
//		for (int i = 0; i < size; i++) {
//			// 判断代理商是否为主代理
//			if (agents.get(i).getParentId() == 0) {
//				// 剩余
//				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), null));
//			} else {
//				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), agents.get(i + 1)));
//			}
//		}
//
//		checkProfit(list);// 校验结果
//
//		return list;
//	}
//
//	// 对订单进行分润（快捷支付）
//	public static List<ProfitDomain> createProfitQuick(MARDomain mar, OrderDomain order) {
//		List<ProfitDomain> list = new ArrayList<ProfitDomain>();
//		LinkedList<UserDomain> agents = new LinkedList<>(mar.getAgents());
//		int size = mar.getAgents().size();
//		if (size == 0) {
//			return null;
//		}
//		// 商户代理分润
//		list.add(buildStmDomain(order, mar.getMerchant(), agents.getFirst()));
//
//		// 代理分润
//		for (int i = 0; i < size; i++) {
//			// 判断代理商是否为主代理
//			if (agents.get(i).getParentId() == 0) {
//				// 剩余
//				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), null));
//			} else {
//				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), agents.get(i + 1)));
//			}
//		}
//		checkProfit(list);// 校验结果
//		return list;
//	}
//
//	// 处理商户跟代理商分润
//	private static ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent) {
//
//		ProfitDomain profitDomain = new ProfitDomain();
//		profitDomain.setOrderId(order.getOrderId());
//		profitDomain.setAccount(order.getAccount());
//		profitDomain.setAmount(order.getAmount());
//
//		profitDomain.setMerchantProfit(
//				compute(order.getAmount(), 1 - getMerchantRate(merchantDomain, order.getChannelCode())));
//		profitDomain
//				.setTotalProfit(compute(order.getAmount(), getMerchantRate(merchantDomain, order.getChannelCode())));
//
//		profitDomain.setAgentId(agent.getId());
//		profitDomain.setAgentProfit(compute(order.getAmount(),
//				getMerchantRate(merchantDomain, order.getChannelCode()) - getAgentRate(agent, order.getChannelCode())));
//
//		profitDomain.setTjProfit(0d);// 赋默认值
//		// 1:开启 0:关闭
//		if (agent.getTjStatus() == 1) {
//			if (order.getAmount() >= agent.getTjLimit()) {
//				if (merchantDomain.getTjId() == null) {
//					// 无推荐人
//				} else {
//					profitDomain.setTjId(merchantDomain.getTjId());
//					profitDomain.setTjProfit(compute(profitDomain.getAgentProfit(), agent.getTjRate()));
//					profitDomain.setAgentProfit(profitDomain.getAgentProfit() - profitDomain.getTjProfit());
//				}
//			}
//		}
//
//		profitDomain.setOrderDate(order.getDate());
//		profitDomain.setCreateDate(new Date());
//		profitDomain.setStatus(1);
//
//		return profitDomain;
//	};
//
//	// 处理代理商跟代理商分润
//	private static ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent,
//			UserDomain parent) {
//
//		ProfitDomain profitDomain = new ProfitDomain();
//		profitDomain.setTjProfit(0d);// 赋默认值
//
//		if (parent == null) {
//			// 到了总代
//
//			profitDomain.setOrderId(order.getOrderId());
//			profitDomain.setAccount(order.getAccount());
//			profitDomain.setAmount(order.getAmount());
//			profitDomain.setMerchantProfit(
//					compute(order.getAmount(), 1 - getMerchantRate(merchantDomain, order.getChannelCode())));
//			profitDomain.setTotalProfit(
//					compute(order.getAmount(), getMerchantRate(merchantDomain, order.getChannelCode())));
//
//			profitDomain.setAgentId(agent.getParentId());
//			profitDomain.setAgentProfit(compute(order.getAmount(), getAgentRate(agent, order.getChannelCode())));
//			profitDomain.setOrderDate(order.getDate());
//			profitDomain.setCreateDate(new Date());
//			profitDomain.setStatus(1);
//			return profitDomain;
//		}
//
//		profitDomain.setOrderId(order.getOrderId());
//		profitDomain.setAccount(order.getAccount());
//		profitDomain.setAmount(order.getAmount());
//		profitDomain.setMerchantProfit(
//				compute(order.getAmount(), 1 - getMerchantRate(merchantDomain, order.getChannelCode())));
//		profitDomain
//				.setTotalProfit(compute(order.getAmount(), getMerchantRate(merchantDomain, order.getChannelCode())));
//
//		profitDomain.setAgentId(parent.getId());
//		profitDomain.setAgentProfit(compute(order.getAmount(),
//				getAgentRate(agent, order.getChannelCode()) - getAgentRate(parent, order.getChannelCode())));
//
//		profitDomain.setOrderDate(order.getDate());
//		profitDomain.setCreateDate(new Date());
//		profitDomain.setStatus(1);
//		return profitDomain;
//	}
//
//	/**
//	 * 实时计算余额
//	 * 
//	 * @return
//	 */
//	public static boolean realtimeComputationBalance(List<ProfitDomain> list) {
//		for (ProfitDomain profitDomain : list) {
//			if (profitDomain.getTjId() != null && profitDomain.getTjId() != 0) {
//				if (profitDomain.getTjProfit() != null && profitDomain.getTjProfit() != 0) {
//					QueueManager.getInstance().pushTask(new BalanceEntity(profitDomain));
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 重算
//	 * 
//	 * @param list
//	 * @return
//	 */
//	public static boolean recalculationComputationBalance(List<ProfitDomain> list) {
//		for (ProfitDomain profitDomain : list) {
//			if (profitDomain.getTjId() != null && profitDomain.getTjId() != 0) {
//				if (profitDomain.getTjProfit() != null && profitDomain.getTjProfit() != 0) {
//					QueueManager.getInstance().pushTask(new BalanceEntity(profitDomain).setType(3));
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	public static boolean realtimeComputationBalance(WithdrawalsDomain withdrawalsDomain) {
//
//		if (withdrawalsDomain.getAmount() != null && withdrawalsDomain.getAmount() != 0) {
//			QueueManager.getInstance().pushTask(new BalanceEntity(withdrawalsDomain));
//			return true;
//		}
//		return false;
//	}
//
//	public static Double sum(Double d1, Double d2) {
//		if (d1 == null) {
//			d1 = 0d;
//		}
//		if (d2 == null) {
//			d2 = 0d;
//		}
//
//		return d1 + d2;
//	}
//
//	/**
//	 * 计算方式
//	 * 
//	 * @param money
//	 * @param rate
//	 * @return
//	 */
//	private static Double compute(Double money, Double rate) {
//		return getDouble(money * rate, 2);
//	}
//
//	private static double getDouble(double a, int b) {
//		BigDecimal aa = new BigDecimal(Double.toString(a));
//		BigDecimal c = new BigDecimal(1);
//		return aa.divide(c, b, RoundingMode.DOWN).doubleValue();
//	}
//
//	/**
//	 * 校验最后结果
//	 * 
//	 * @param list
//	 */
//	private static void checkProfit(List<ProfitDomain> list) {
//		BigDecimal sum = new BigDecimal(0);
//		for (int i = 0; i < list.size(); i++) {
//			if ((i + 1) == list.size()) {// 最后一位
//				if (sum.add(BigDecimal.valueOf(list.get(i).getAgentProfit()))
//						.compareTo(BigDecimal.valueOf(list.get(i).getTotalProfit())) != 0) {
//					list.get(i).setAgentProfit(
//							BigDecimal.valueOf(list.get(i).getTotalProfit()).subtract(sum).doubleValue());
//				}
//			}
//			sum = sum.add(BigDecimal.valueOf(list.get(i).getAgentProfit())
//					.add(BigDecimal.valueOf(list.get(i).getTjProfit())));
//		}
//
//		// double sum = 0;
//		//
//		// for (int i = 0; i < list.size(); i++) {
//		// if ((i + 1) == list.size()) {// 最后一位
//		//
//		// //sum != list.get(i).getAgentProfit()
//		// if((sum+list.get(i).getAgentProfit())!=list.get(i).getTotalProfit()){
//		// list.get(i).setAgentProfit(list.get(i).getTotalProfit() - sum);
//		// }
//		// }
//		// sum += (list.get(i).getAgentProfit() + list.get(i).getTjProfit());
//		// }
//
//	}
//
//	/**
//	 * 返回商户费率，默认返回微信费率
//	 * 
//	 * @param merchantDomain
//	 * @return
//	 */
//	private static Double getMerchantRate(MerchantDomain merchantDomain, Integer channel) {
//		// 微信or支付宝//默认1，微信
//		Double rate = null;
//		switch (channel == null ? DEFAULT_PAY : channel) {
//		case 1:// 微信
//			rate = merchantDomain.getWxRate();
//			break;
//		case 2:// 支付宝
//			rate = merchantDomain.getWxRate();
//			break;
//		case 3:// 快捷
//			rate = merchantDomain.getUnipayRate();
//			break;
//		default:
//			rate = merchantDomain.getWxRate();
//			break;
//		}
//		if (rate == null || rate == 0) {
//			throw new RuntimeException(merchantDomain.getAccount() + "费率为空,无法计算分润..");
//		}
//		return rate;
//	}
//
//	/**
//	 * 返回代理商费率，默认返回微信费率
//	 * 
//	 * @param merchantDomain
//	 * @return
//	 */
//	private static Double getAgentRate(UserDomain userDomain, Integer channel) {
//		// 微信or支付宝
//		Double rate = null;
//		switch (channel == null ? DEFAULT_PAY : channel) {
//		case 1:// 微信cost_wrate
//			rate = userDomain.getCostWrate();
//			break;
//		case 2:// 支付宝cost_arate
//			rate = userDomain.getCostArate();
//			break;
//		case 3:// 快捷支付cost_krate
//			rate = userDomain.getCostKrate();
//			break;
//		default:
//			rate = userDomain.getCostWrate();
//			break;
//		}
//		if (rate == null || rate == 0) {
//			throw new RuntimeException(userDomain.getAccount() + "费率为空,无法计算分润..");
//		}
//		return rate;
//	}

}
