package sds.webapp.ord.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.domain.UserDomain;
import sds.webapp.ord.dao.OrderDAO;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;

@TransactionService
public class OrderServiceImpl implements OrderService {

	@TransactionDAO
	private OrderDAO orderDAO;

	@Override
	public int insert(OrderDomain t) {
		// TODO Auto-generated method stub
		return orderDAO.insert(t);
	}

	@Override
	public int delete(OrderDomain t) {
		// TODO Auto-generated method stub
		return orderDAO.delete(t);
	}

	@Override
	public int update(OrderDomain t) {
		// TODO Auto-generated method stub
		return orderDAO.update(t);
	}

	@Override
	public OrderDomain findByKey(OrderDomain t) {
		// TODO Auto-generated method stub
		return orderDAO.findByKey(t);
	}

	@Override
	public List<OrderDomain> findByWhere(OrderDomain t) {
		// TODO Auto-generated method stub
		return orderDAO.findByWhere(t);
	}

	@Override
	public List<OrderDomain> getAllCheckedOrder(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return orderDAO.getAllCheckedOrder(orderDomain);
	}

	@Override
	public List<OrderDomain> getAllUnCheckOrder(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return orderDAO.getAllUnCheckOrder(orderDomain);
	}

	@Override
	public String getTotalAmountByOrder(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return orderDAO.getTotalAmountByOrder(orderDomain);
	}

	@Override
	public List<OrderDomain> getOrderByUser(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return orderDAO.getOrderByUser(orderDomain);
	}

}
