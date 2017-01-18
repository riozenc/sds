package sds.webapp.stm.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.stm.dao.ProfitUserDAO;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.service.ProfitUserService;

@TransactionService
public class ProfitUserServiceImpl implements ProfitUserService {

	@TransactionDAO
	private ProfitUserDAO profitUserDAO;

	@Override
	public int insert(ProfitUserDomain t) {
		// TODO Auto-generated method stub
		return profitUserDAO.insert(t);
	}

	@Override
	public int delete(ProfitUserDomain t) {
		// TODO Auto-generated method stub
		return profitUserDAO.delete(t);
	}

	@Override
	public int update(ProfitUserDomain t) {
		// TODO Auto-generated method stub
		return profitUserDAO.update(t);
	}

	@Override
	public ProfitUserDomain findByKey(ProfitUserDomain t) {
		// TODO Auto-generated method stub
		return profitUserDAO.findByKey(t);
	}

	@Override
	public List<ProfitUserDomain> findByWhere(ProfitUserDomain t) {
		// TODO Auto-generated method stub
		return profitUserDAO.findByWhere(t);
	}

	@Override
	public int insertBatch(List<ProfitUserDomain> list) {
		// TODO Auto-generated method stub
		return profitUserDAO.insertBatch(list);
	}

}
