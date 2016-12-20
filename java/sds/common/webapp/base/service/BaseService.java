/**
 * 	Title:sds.common.webapp.base.service
 *		Datetime:2016年12月16日 下午5:54:42
 *		Author:czy
 */
package sds.common.webapp.base.service;

import java.util.List;

public interface BaseService<T> {
	public int insert(T t);

	public int delete(T t);

	public int update(T t);

	public T findByKey(T t);

	public List<T> findByWhere(T t);
}
