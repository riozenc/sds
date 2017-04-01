package sds.webapp.ord.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.domain.OrderDomain;

public interface OrderService extends BaseService<OrderDomain> {

	/**
	 * 指定status=3
	 * 
	 * @param orderDomain
	 * @return
	 */
	public List<OrderDomain> getAllCheckedOrder(OrderDomain orderDomain);

	/**
	 * 指定status=1
	 * 
	 * @param orderDomain
	 * @return
	 */
	public List<OrderDomain> getAllUnCheckOrder(OrderDomain orderDomain);

	public String getTotalAmountByOrder(OrderDomain orderDomain);

	public List<OrderDomain> getOrderByUser(OrderDomain orderDomain);
}
