package sds.webapp.sys.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.dao.UserDAO;
import sds.webapp.sys.dao.ConfDAO;
import sds.webapp.sys.domain.ConfDomain;
import sds.webapp.sys.service.ConfService;

@TransactionService
public class ConfServiceImpl implements ConfService {

	@TransactionDAO
	private ConfDAO confDAO;
	
	@TransactionDAO
	private UserDAO userDAO;

	@Override
	public int insert(ConfDomain t) {
		// TODO Auto-generated method stub
		return confDAO.insert(t);
	}

	@Override
	public int delete(ConfDomain t) {
		// TODO Auto-generated method stub
		return confDAO.delete(t);
	}

	@Override
	public int update(ConfDomain t) {
		// TODO Auto-generated method stub
		return confDAO.update(t);
	}

	@Override
	public ConfDomain findByKey(ConfDomain t) {
		// TODO Auto-generated method stub
		return confDAO.findByKey(t);
	}

	@Override
	public List<ConfDomain> findByWhere(ConfDomain t) {
		// TODO Auto-generated method stub
		return confDAO.findByWhere(t);
	}

}
