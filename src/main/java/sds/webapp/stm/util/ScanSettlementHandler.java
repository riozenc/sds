/**
 * Title:ScanSettlementHandler.java
 * Author:riozenc
 * Datetime:2017年3月27日 下午7:24:32
**/
package sds.webapp.stm.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;

/**
 * 扫码支付
 * 
 * @author riozenc
 *
 */
public class ScanSettlementHandler extends SettlementHandler {

	@Override
	public List<ProfitDomain> createProfit(MARDomain mar, OrderDomain order) {
		// TODO Auto-generated method stub
		List<ProfitDomain> list = new ArrayList<ProfitDomain>();
		LinkedList<UserDomain> agents = new LinkedList<>(mar.getAgents());
		int size = mar.getAgents().size();
		if (size == 0) {
			return null;
		}
		// 商户代理分润
		list.add(buildStmDomain(order, mar.getMerchant(), agents.getFirst()));

		// 代理分润
		for (int i = 0; i < size; i++) {
			// 判断代理商是否为主代理
			if (agents.get(i).getParentId() == 0) {
				// 剩余
				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), null));
			} else {
				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), agents.get(i + 1)));
			}
		}

		checkProfit(list);// 校验结果

		return list;
	}

	// 处理商户跟代理商分润
	private ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent) {

		ProfitDomain profitDomain = new ProfitDomain();
		profitDomain.setOrderId(order.getOrderId());
		profitDomain.setAccount(order.getAccount());
		profitDomain.setAmount(order.getAmount());

		profitDomain.setMerchantProfit(
				multiply(order.getAmount(), 1 - getMerchantRate(merchantDomain, order.getChannelCode())));
		profitDomain
				.setTotalProfit(multiply(order.getAmount(), getMerchantRate(merchantDomain, order.getChannelCode())));

		profitDomain.setAgentId(agent.getId());
		profitDomain.setAgentProfit(multiply(order.getAmount(),
				getMerchantRate(merchantDomain, order.getChannelCode()) - getAgentRate(agent, order.getChannelCode())));

		profitDomain.setTjProfit(0d);// 赋默认值
		// 1:开启 0:关闭
		if (agent.getTjStatus() == 1) {
			if (order.getAmount() >= agent.getTjLimit()) {
				if (merchantDomain.getTjId() == null) {
					// 无推荐人
				} else {
					profitDomain.setTjId(merchantDomain.getTjId());
					profitDomain.setTjProfit(multiply(profitDomain.getAgentProfit(), agent.getTjRate()));
					profitDomain.setAgentProfit(BigDecimal.valueOf(profitDomain.getAgentProfit()).subtract(BigDecimal.valueOf(profitDomain.getTjProfit())).doubleValue());
				}
			}
		}

		profitDomain.setOrderDate(order.getDate());
		profitDomain.setCreateDate(new Date());
		profitDomain.setStatus(1);

		return profitDomain;
	};

	// 处理代理商跟代理商分润
	private ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent,
			UserDomain parent) {

		ProfitDomain profitDomain = new ProfitDomain();
		profitDomain.setTjProfit(0d);// 赋默认值

		if (parent == null) {
			// 到了总代

			profitDomain.setOrderId(order.getOrderId());
			profitDomain.setAccount(order.getAccount());
			profitDomain.setAmount(order.getAmount());
			profitDomain.setMerchantProfit(
					multiply(order.getAmount(), 1 - getMerchantRate(merchantDomain, order.getChannelCode())));
			profitDomain.setTotalProfit(
					multiply(order.getAmount(), getMerchantRate(merchantDomain, order.getChannelCode())));

			profitDomain.setAgentId(agent.getParentId());
			profitDomain.setAgentProfit(multiply(order.getAmount(), getAgentRate(agent, order.getChannelCode())));
			profitDomain.setOrderDate(order.getDate());
			profitDomain.setCreateDate(new Date());
			profitDomain.setStatus(1);
			return profitDomain;
		}

		profitDomain.setOrderId(order.getOrderId());
		profitDomain.setAccount(order.getAccount());
		profitDomain.setAmount(order.getAmount());
		profitDomain.setMerchantProfit(
				multiply(order.getAmount(), 1 - getMerchantRate(merchantDomain, order.getChannelCode())));
		profitDomain
				.setTotalProfit(multiply(order.getAmount(), getMerchantRate(merchantDomain, order.getChannelCode())));

		profitDomain.setAgentId(parent.getId());
		profitDomain.setAgentProfit(multiply(order.getAmount(),
				getAgentRate(agent, order.getChannelCode()) - getAgentRate(parent, order.getChannelCode())));

		profitDomain.setOrderDate(order.getDate());
		profitDomain.setCreateDate(new Date());
		profitDomain.setStatus(1);
		return profitDomain;
	}

}
