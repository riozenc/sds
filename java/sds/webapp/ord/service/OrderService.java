package sds.webapp.ord.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.ord.domain.OrderDomain;

public interface OrderService extends BaseService<OrderDomain> {

	public List<OrderDomain> getAllCheckedOrder(OrderDomain orderDomain);

	public List<OrderDomain> getAllUnCheckOrder(OrderDomain orderDomain);

	public String getTotalAmountByOrder(OrderDomain orderDomain);
}
