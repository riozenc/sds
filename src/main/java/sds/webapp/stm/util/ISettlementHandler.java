/**
 * Title:ISettlementHandler.java
 * Author:riozenc
 * Datetime:2017年3月27日 下午7:16:49
**/
package sds.webapp.stm.util;

import java.util.List;

import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.domain.MARDomain;
import sds.webapp.stm.domain.ProfitDomain;

public interface ISettlementHandler {

	/**
	 * 通过订单获取分润
	 * 
	 * @param mar
	 * @param order
	 * @return
	 */
	public List<ProfitDomain> createProfit(MARDomain mar, OrderDomain order);

}
