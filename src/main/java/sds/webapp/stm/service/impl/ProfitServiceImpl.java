package sds.webapp.stm.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.stm.dao.ProfitDAO;
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.service.ProfitService;

@TransactionService
public class ProfitServiceImpl implements ProfitService {

	@TransactionDAO
	private ProfitDAO profitDAO;

	@Override
	public int insert(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.insert(t);
	}

	@Override
	public int delete(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.delete(t);
	}

	@Override
	public int update(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.update(t);
	}

	@Override
	public ProfitDomain findByKey(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.findByKey(t);
	}

	@Override
	public List<ProfitDomain> findByWhere(ProfitDomain t) {
		// TODO Auto-generated method stub
		return profitDAO.findByWhere(t);
	}

	@Override
	public int insertBatch(List<ProfitDomain> list) {
		// TODO Auto-generated method stub
		return profitDAO.insertBatch(list);
	}

}
