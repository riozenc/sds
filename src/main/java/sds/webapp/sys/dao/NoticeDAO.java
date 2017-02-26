/**
 * Title:NoticeDAO.java
 * Author:riozenc
 * Datetime:2017年2月26日 上午10:33:04
**/
package sds.webapp.sys.dao;

import java.util.List;

import com.riozenc.quicktool.annotation.TransactionDAO;
import com.riozenc.quicktool.mybatis.dao.AbstractTransactionDAOSupport;
import com.riozenc.quicktool.mybatis.dao.BaseDAO;

import sds.webapp.sys.domain.NoticeDomain;

@TransactionDAO
public class NoticeDAO extends AbstractTransactionDAOSupport implements BaseDAO<NoticeDomain> {

	@Override
	public int insert(NoticeDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().insert(getNamespace() + ".insert", t);
	}

	@Override
	public int delete(NoticeDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().delete(getNamespace() + ".delete", t);
	}

	@Override
	public int update(NoticeDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().update(getNamespace() + ".update", t);
	}

	@Override
	public NoticeDomain findByKey(NoticeDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().load(getNamespace() + ".findByKey", t);
	}

	@Override
	public List<NoticeDomain> findByWhere(NoticeDomain t) {
		// TODO Auto-generated method stub
		return getPersistanceManager().find(getNamespace() + ".findByWhere", t);
	}

}
