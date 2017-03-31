/**
 * Title:SettlementHandler.java
 * Author:riozenc
 * Datetime:2017年3月27日 下午7:27:29
**/
package sds.webapp.stm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sds.common.queue.entity.BalanceEntity;
import sds.common.queue.manager.QueueManager;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.domain.WithdrawalsDomain;

public abstract class SettlementHandler implements ISettlementHandler {
	private final static int DEFAULT_PAY = 1;// 微信

	private final static ScanSettlementHandler scan = new ScanSettlementHandler();
	private final static PurchaseSettlementHandler purchase = new PurchaseSettlementHandler();

	public static SettlementHandler getScanInstance() {
		return scan;
	}

	public static SettlementHandler getPurchaseInstance() {
		return purchase;
	}

	/**
	 * 根据分润明细计算代理商分润
	 * 
	 * @param list
	 *            分润明细list
	 * @return
	 */
	public static List<ProfitUserDomain> computeProfitByUser(List<ProfitDomain> list) {
		Map<Integer, ProfitUserDomain> profitUserMap = new HashMap<Integer, ProfitUserDomain>();
		list.stream().forEach((profit) -> {

			ProfitUserDomain profitUserDomain = profitUserMap.get(profit.getAgentId());

			if (profitUserDomain == null) {
				profitUserDomain = new ProfitUserDomain();
				profitUserDomain.setAgentId(profit.getAgentId());
				profitUserDomain.setAgentName(profit.getAgentName());
				profitUserDomain.setCmer(profit.getCmer());
				profitUserMap.put(profit.getAgentId(), profitUserDomain);
			}
			profitUserDomain.setAccount(profit.getAgentAccount());
			profitUserDomain.setTotalAmount(sum(profitUserDomain.getTotalAmount(), profit.getAmount()));
			profitUserDomain.setTotalProfit(sum(profitUserDomain.getTotalProfit(), profit.getAgentProfit()));
			profitUserDomain.setDate(profit.getOrderDate());
			profitUserDomain.setStatus(0);

		});

		List<ProfitUserDomain> profitUserDomains = new ArrayList<>(profitUserMap.values());
		return profitUserDomains;
	}

	/**
	 * 实时计算余额
	 * 
	 * @return
	 */
	public static boolean realtimeComputationBalance(List<ProfitDomain> list) {
		for (ProfitDomain profitDomain : list) {
			if (profitDomain.getTjId() != null && profitDomain.getTjId() != 0) {
				if (profitDomain.getTjProfit() != null && profitDomain.getTjProfit() != 0) {
					QueueManager.getInstance().pushTask(new BalanceEntity(profitDomain));
				}
			}
		}
		return false;
	}

	/**
	 * 重算余额
	 * 
	 * @param list
	 * @return
	 */
	public static boolean recalculationComputationBalance(List<ProfitDomain> list) {
		for (ProfitDomain profitDomain : list) {
			if (profitDomain.getTjId() != null && profitDomain.getTjId() != 0) {
				if (profitDomain.getTjProfit() != null && profitDomain.getTjProfit() != 0) {
					QueueManager.getInstance().pushTask(new BalanceEntity(profitDomain).setType(3));
				}
			}
		}
		return false;
	}

	/**
	 * 计算提现余额
	 * 
	 * @param withdrawalsDomain
	 * @return
	 */
	public static boolean realtimeComputationBalance(WithdrawalsDomain withdrawalsDomain) {

		if (withdrawalsDomain.getAmount() != null && withdrawalsDomain.getAmount() != 0) {
			QueueManager.getInstance().pushTask(new BalanceEntity(withdrawalsDomain));
		}
		return false;
	}

	/**
	 * 相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double sum(Double d1, Double d2) {
		if (d1 == null) {
			d1 = 0d;
		}
		if (d2 == null) {
			d2 = 0d;
		}
		return BigDecimal.valueOf(d1).add(BigDecimal.valueOf(d2)).doubleValue();
	}

	/**
	 * 相乘
	 * 
	 * @param money
	 * @param rate
	 * @return
	 */
	protected Double multiply(Double d1, Double d2) {
		return getDouble(d1 * d2, 2);
	}

	protected double getDouble(double a, int b) {
		BigDecimal aa = new BigDecimal(Double.toString(a));
		BigDecimal c = new BigDecimal(1);
		return aa.divide(c, b, RoundingMode.DOWN).doubleValue();
	}

	/**
	 * 校验最后结果
	 * 
	 * @param list
	 */
	protected void checkProfit(List<ProfitDomain> list) {
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < list.size(); i++) {
			if ((i + 1) == list.size()) {// 最后一位
				if (sum.add(BigDecimal.valueOf(list.get(i).getAgentProfit()))
						.compareTo(BigDecimal.valueOf(list.get(i).getTotalProfit())) != 0) {
					list.get(i).setAgentProfit(
							BigDecimal.valueOf(list.get(i).getTotalProfit()).subtract(sum).doubleValue());
				}
			}
			sum = sum.add(BigDecimal.valueOf(list.get(i).getAgentProfit())
					.add(BigDecimal.valueOf(list.get(i).getTjProfit())));
		}

	}

	/**
	 * 返回商户费率，默认返回微信费率
	 * 
	 * @param merchantDomain
	 * @return
	 */
	protected Double getMerchantRate(MerchantDomain merchantDomain, Integer channel) {
		// 微信or支付宝//默认1，微信
		Double rate = null;
		switch (channel == null ? DEFAULT_PAY : channel) {
		case 1:// 微信
			rate = merchantDomain.getWxRate();
			break;
		case 2:// 支付宝
			rate = merchantDomain.getWxRate();
			break;
		case 3:// 快捷
			rate = merchantDomain.getUnipayRate();
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
	protected Double getAgentRate(UserDomain userDomain, Integer channel) {
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
