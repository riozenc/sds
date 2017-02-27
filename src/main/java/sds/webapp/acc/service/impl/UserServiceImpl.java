/**
 * 	Title:sds.webapp.acc.service.impl
 *		Datetime:2016年12月16日 下午5:55:23
 *		Author:czy
 */
package sds.webapp.acc.service.impl;

import java.util.Date;
import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;
import com.riozenc.quicktool.common.util.cryption.en.WebPasswordUtils;

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
		t.setPassword(WebPasswordUtils.encodePassword(t.getPassword()));
		t.setCreateDate(new Date());
		t.setStatus(0);
		return userDAO.insert(t);
	}

	@Override
	public int delete(UserDomain t) {
		// TODO Auto-generated method stub
		return userDAO.delete(t);
	}

	@Override
	public int update(UserDomain t) {
		// TODO Auto-generated method stub

		if (t.getPassword() != null) {
			t.setPassword(WebPasswordUtils.encodePassword(t.getPassword()));
		}
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

	@Override
	public int checkUser(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return userDAO.checkUser(userDomain);
	}

	@Override
	public int updateRate(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return userDAO.updateRate(userDomain);
	}

	@Override
	public List<UserDomain> getAllCheckedUser() {
		// TODO Auto-generated method stub
		return userDAO.getAllCheckedUser();
	}

	@Override
	public List<UserDomain> findSubUserList(UserDomain userDomain) {
		// TODO Auto-generated method stub
		return userDAO.findSubUserList(userDomain);
	}

}
