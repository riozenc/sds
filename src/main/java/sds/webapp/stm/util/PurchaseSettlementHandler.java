/**
 * Title:PurchaseSettlementHandler.java
 * Author:riozenc
 * Datetime:2017年3月27日 下午7:16:00
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
 * 购卡
 * 
 * @author riozenc
 *
 */
public class PurchaseSettlementHandler extends SettlementHandler {

	// private final static int min_amount = 3;// 最小手续费
	// private final static double fee = 0.001;// 商户费率
	
	
	public void test(){
		
	}
	

	@Override
	public List<ProfitDomain> createProfit(MARDomain mar, OrderDomain order) {
		// TODO Auto-generated method stub
		List<ProfitDomain> list = new ArrayList<ProfitDomain>();
		LinkedList<UserDomain> agents = new LinkedList<>(mar.getAgents());
		int size = mar.getAgents().size();
		if (size == 0) {
			return null;
		}

		boolean isProfit = isProfit(order);

		// 商户代理分润
		list.add(buildStmDomain(order, mar.getMerchant(), agents.getFirst(), isProfit));

		// 代理分润
		for (int i = 0; i < size; i++) {
			// 判断代理商是否为主代理
			if (agents.get(i).getParentId() == 0) {
				// 剩余
				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), null, isProfit));
			} else {
				list.add(buildStmDomain(order, mar.getMerchant(), agents.get(i), agents.get(i + 1), isProfit));
			}
		}

		checkProfit(list);// 校验结果

		return list;
	}

	// 计算订单交易金额是否分润
	private boolean isProfit(OrderDomain orderDomain) {
		return multiply(orderDomain.getAmount(), orderDomain.getFee()) > orderDomain.getMinamount();
	}

	// 处理商户跟代理商分润
	private ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent,
			boolean isProfit) {

		ProfitDomain profitDomain = new ProfitDomain();
		profitDomain.setOrderId(order.getOrderId());
		profitDomain.setAccount(order.getAccount());
		profitDomain.setAmount(order.getAmount());

		if (isProfit) {
			// 费率分润
			profitDomain.setMerchantProfit(multiply(order.getAmount(),
					BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(order.getFee())).doubleValue()));
			profitDomain.setTotalProfit(multiply(order.getAmount(), order.getFee()));
			profitDomain.setAgentProfit(multiply(order.getAmount(), BigDecimal.valueOf(order.getFee())
					.subtract(BigDecimal.valueOf(getAgentRate(agent, order.getChannelCode()))).doubleValue()));

		} else {
			// 保底3
			profitDomain.setMerchantProfit(order.getAmount() - order.getMinamount());// 交易金额
			profitDomain.setTotalProfit(order.getMinamount());
			profitDomain.setAgentProfit(0d);

		}
		profitDomain.setAgentId(agent.getId());
		profitDomain.setTjProfit(0d);// 赋默认值
		// 1:开启 0:关闭
		if (agent.getTjStatus() == 1 && isProfit) {
			if (order.getAmount() >= agent.getTjLimit()) {
				if (merchantDomain.getTjId() == null) {
					// 无推荐人
				} else {
					profitDomain.setTjId(merchantDomain.getTjId());
					profitDomain.setTjProfit(multiply(profitDomain.getAgentProfit(), agent.getTjRate()));
					profitDomain.setAgentProfit(profitDomain.getAgentProfit() - profitDomain.getTjProfit());
				}
			}
		}

		profitDomain.setOrderDate(order.getDate());
		profitDomain.setCreateDate(new Date());
		profitDomain.setStatus(1);

		return profitDomain;
	}

	// 处理代理商跟代理商分润(生成的是上级分润)
	private ProfitDomain buildStmDomain(OrderDomain order, MerchantDomain merchantDomain, UserDomain agent,
			UserDomain parent, boolean isProfit) {

		ProfitDomain profitDomain = new ProfitDomain();

		profitDomain.setOrderId(order.getOrderId());
		profitDomain.setAccount(order.getAccount());
		profitDomain.setAmount(order.getAmount());
		profitDomain.setTjProfit(0d);// 赋默认值

		if (isProfit) {
			// 费率分润
			profitDomain.setMerchantProfit(multiply(order.getAmount(),
					BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(order.getFee())).doubleValue()));
			profitDomain.setTotalProfit(multiply(order.getAmount(), order.getFee()));

		} else {
			// 保底3
			profitDomain.setMerchantProfit(order.getAmount() - order.getMinamount());//
			profitDomain.setTotalProfit(order.getMinamount());

		}

		if (parent == null) {
			// 到了总代
			profitDomain.setAgentId(agent.getParentId());

			if (multiply(order.getAmount(), getAgentRate(agent, order.getChannelCode())) > order.getMinamount()) {
				profitDomain.setAgentProfit(multiply(order.getAmount(), getAgentRate(agent, order.getChannelCode())));
			} else {
				profitDomain.setAgentProfit(order.getMinamount());
			}

		} else {
			profitDomain.setAgentId(parent.getId());
			if (multiply(order.getAmount(), getAgentRate(parent, order.getChannelCode())) > order.getMinamount()) {
				profitDomain.setAgentProfit(multiply(order.getAmount(),
						getAgentRate(agent, order.getChannelCode()) - getAgentRate(parent, order.getChannelCode())));
			} else {
				if ((multiply(order.getAmount(), getAgentRate(agent, order.getChannelCode()))
						- order.getMinamount()) > 0) {
					profitDomain.setAgentProfit(multiply(order.getAmount(), getAgentRate(agent, order.getChannelCode()))
							- order.getMinamount());
				} else {
					profitDomain.setAgentProfit(0d);
				}
			}
		}

		profitDomain.setOrderDate(order.getDate());
		profitDomain.setCreateDate(new Date());
		profitDomain.setStatus(1);

		return profitDomain;
	}
}
