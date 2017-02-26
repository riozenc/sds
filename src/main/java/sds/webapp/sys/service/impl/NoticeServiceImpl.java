/**
 * Title:NoticeServiceImpl.java
 * Author:riozenc
 * Datetime:2017年2月26日 上午10:41:36
**/
package sds.webapp.sys.service.impl;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.annotation.TransactionService;

import sds.webapp.sys.dao.NoticeDAO;
import sds.webapp.sys.domain.NoticeDomain;
import sds.webapp.sys.service.NoticeService;

@TransactionService
public class NoticeServiceImpl implements NoticeService {

	@TransactionDAO
	private NoticeDAO noticeDAO;

	@Override
	public int insert(NoticeDomain t) {
		// TODO Auto-generated method stub
		return noticeDAO.insert(t);
	}

	@Override
	public int delete(NoticeDomain t) {
		// TODO Auto-generated method stub
		return noticeDAO.delete(t);
	}

	@Override
	public int update(NoticeDomain t) {
		// TODO Auto-generated method stub
		return noticeDAO.update(t);
	}

	@Override
	public NoticeDomain findByKey(NoticeDomain t) {
		// TODO Auto-generated method stub
		return noticeDAO.findByKey(t);
	}

	@Override
	public List<NoticeDomain> findByWhere(NoticeDomain t) {
		// TODO Auto-generated method stub
		return noticeDAO.findByWhere(t);
	}

}
