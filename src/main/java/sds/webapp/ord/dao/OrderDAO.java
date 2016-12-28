package sds.webapp.ord.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.ord.domain.OrderDomain;

@TransactionDAO
public class OrderDAO extends AbstractTransactionDAOSupport implements BaseDAO<OrderDomain> {

	@Override
	public int delete(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", orderDomain);
	}

	@Override
	public OrderDomain findByKey(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", orderDomain);
	}

	@Override
	public List<OrderDomain> findByWhere(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", orderDomain);
	}

	@Override
	public int insert(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", orderDomain);
	}

	@Override
	public int update(OrderDomain orderDomain) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", orderDomain);
	}

}
