package sds.webapp.stm.util;

import java.util.ArrayList;
import java.util.List;

import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;

/**
 * 结算工具类
 * 
 * @author riozenc
 *
 */
public class SettlementUtil {

	private final static int DEFAULT_PAY = 1;// 微信

	public static Double computeProfit(Double amount, Double rate) {
		return amount * rate;
	}

	public static List<ProfitDomain> createProfit(MARDomain mar, OrderDomain order) {

		List<ProfitDomain> list = new ArrayList<ProfitDomain>();
		int size = mar.getAgents().size();
		if (size == 0) {
			return null;
		}

		// 商户代理分润
		list.add(buildStmDomain(order, mar.getMerchant(), mar.getAgents().getFirst()));

		// 代理分润
		for (int i = 0; i < size; i++) {
			// 判断代理商是否为主代理
			if (mar.getAgents().getFirst().getParentId() == 0) {
				// 剩余
				list.add(buildStmDomain(order, mar.getMerchant(), mar.getAgents().removeFirst(), null));
			} else {
				list.add(buildStmDomain(order, mar.getMerchant(), mar.getAgents().removeFirst(),
						mar.getAgents().getFirst()));
			}
		}
		
		checkProfit(list);
		return list;
	}

	// 处理商户跟代理商分润
	private static ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent) {

		ProfitDomain profitDomain = new ProfitDomain();
		profitDomain.setOrderId(order.getOrderId());
		profitDomain.setAccount(order.getAccount());
		profitDomain.setAmount(order.getAmount());
		profitDomain
				.setMerchantProfit(order.getAmount() * (1 - getMerchantRate(merchantDomain, order.getChannelCode())));
		profitDomain.setTotalProfit(order.getAmount() * (getMerchantRate(merchantDomain, order.getChannelCode())));

		profitDomain.setAgentId(agent.getId());
		profitDomain.setAgentProfit(
				getMerchantRate(merchantDomain, order.getChannelCode()) - getAgentRate(agent, order.getChannelCode()));
		profitDomain.setOrderDay(order.getDate());

		// StmDomain stmDomain = new StmDomain();
		// stmDomain.setOrderId(order.getId());
		// stmDomain.setMerchantAccount(order.getAccount());
		// stmDomain.setAmount(order.getAmount());
		// stmDomain.setPaymentChannle(order.getChannelCode());
		// stmDomain.setMerchantRate(getMerchantRate(merchantDomain,
		// order.getChannelCode()));
		// stmDomain.setRateDiff(
		// getMerchantRate(merchantDomain, order.getChannelCode()) -
		// getAgentRate(agent, order.getChannelCode()));
		// stmDomain.setAgent(agent);
		// stmDomain.setOrderDate(order.getDate());

		return profitDomain;
	};

	// 处理代理商跟代理商分润
	private static ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent,
			UserDomain parent) {

		ProfitDomain profitDomain = new ProfitDomain();

		if (parent == null) {
			// 到了总代

			profitDomain.setOrderId(order.getOrderId());
			profitDomain.setAccount(order.getAccount());
			profitDomain.setAmount(order.getAmount());
			profitDomain.setMerchantProfit(
					order.getAmount() * (1 - getMerchantRate(merchantDomain, order.getChannelCode())));
			profitDomain.setTotalProfit(order.getAmount() * (getMerchantRate(merchantDomain, order.getChannelCode())));

			profitDomain.setAgentId(agent.getId());
			profitDomain.setAgentProfit(order.getAmount() * (getAgentRate(agent, order.getChannelCode())));
			profitDomain.setOrderDay(order.getDate());
			return profitDomain;
		}

		profitDomain.setOrderId(order.getOrderId());
		profitDomain.setAccount(order.getAccount());
		profitDomain.setAmount(order.getAmount());
		profitDomain
				.setMerchantProfit(order.getAmount() * (1 - getMerchantRate(merchantDomain, order.getChannelCode())));
		profitDomain.setTotalProfit(order.getAmount() * (getMerchantRate(merchantDomain, order.getChannelCode())));

		profitDomain.setAgentId(parent.getId());
		profitDomain.setAgentProfit(order.getAmount()
				* (getAgentRate(agent, order.getChannelCode()) - getAgentRate(parent, order.getChannelCode())));
		profitDomain.setOrderDay(order.getDate());

		// StmDomain stmDomain = new StmDomain();
		// stmDomain.setOrderId(order.getOrderId());
		// stmDomain.setMerchantAccount(order.getAccount());
		// stmDomain.setAmount(order.getAmount());
		// stmDomain.setPaymentChannle(order.getChannelCode());
		// stmDomain.setMerchantRate(getMerchantRate(merchantDomain,
		// order.getChannelCode()));
		// stmDomain.setRateDiff(getAgentRate(agent,
		// order.getChannelCode())-getAgentRate(parent,
		// order.getChannelCode()));
		// stmDomain.setAgent(agent);
		// stmDomain.setOrderDate(order.getDate());

		return profitDomain;
	}
	
	private static void checkProfit(List<ProfitDomain> list) {
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

	/**
	 * 返回商户费率，默认返回微信费率
	 * 
	 * @param merchantDomain
	 * @return
	 */
	private static Double getMerchantRate(MerchantDomain merchantDomain, Integer channel) {
		// 微信or支付宝//默认1，微信
		Double rate = null;
		switch (channel == null ? DEFAULT_PAY : channel) {
		case 1:// 微信
			rate = merchantDomain.getWxRate();
			break;
		case 2:// 支付宝
			rate = merchantDomain.getWxRate();
			break;
		default:
			rate = merchantDomain.getWxRate();
			break;
		}
		if (rate == null || rate == 0) {
			throw new RuntimeException(merchantDomain.getAccount() + "费率为空,无法计算分润..");
		}
		return rate;
	}

	/**
	 * 返回代理商费率，默认返回微信费率
	 * 
	 * @param merchantDomain
	 * @return
	 */
	private static Double getAgentRate(UserDomain userDomain, Integer channel) {
		// 微信or支付宝
		Double rate = null;
		switch (channel == null ? DEFAULT_PAY : channel) {
		case 1:// 微信cost_wrate
			rate = userDomain.getCostWrate();
			break;
		case 2:// 支付宝cost_arate
			rate = userDomain.getCostArate();
			break;
		case 3:// 快捷支付cost_krate
			rate = userDomain.getCostKrate();
			break;
		default:
			rate = userDomain.getCostWrate();
			break;
		}
		if (rate == null || rate == 0) {
			throw new RuntimeException(userDomain.getAccount() + "费率为空,无法计算分润..");
		}
		return rate;
	}

}
