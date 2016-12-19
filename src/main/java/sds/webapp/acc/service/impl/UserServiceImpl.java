/**
 * 	Title:sds.webapp.acc.service.impl
 *		Datetime:2016年12月16日 下午5:55:23
 *		Author:czy
 */
package sds.webapp.acc.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.acc.dao.UserDAO;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.UserService;

@TransactionService
public class UserServiceImpl implements UserService {

	@TransactionDAO
	private UserDAO userDAO;

	@Override
	public int insert(UserDomain t) {
		// TODO Auto-generated method stub
		if (null == userDAO.findByKey(t)) {
			return userDAO.insert(t);
		} else {
			return -1;// 已经存在
		}
	}

	@Override
	public int delete(UserDomain t) {
		// TODO Auto-generated method stub
		return userDAO.delete(t);
	}

	@Override
	public int update(UserDomain t) {
		// TODO Auto-generated method stub
		return userDAO.update(t);
	}

	@Override
	public UserDomain findByKey(UserDomain t) {
		// TODO Auto-generated method stub
		return userDAO.findByKey(t);
	}

	@Override
	public List<UserDomain> findByWhere(UserDomain t) {
		// TODO Auto-generated method stub
		return userDAO.findByWhere(t);
	}

	@Override
	public UserDomain getUser(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return userDAO.getUser(userDomain);
	}

}
