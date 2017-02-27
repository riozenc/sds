/**
 * 	Title:sds.webapp.acc.service
 *		Datetime:2016年12月16日 下午5:51:04
 *		Author:czy
 */
package sds.webapp.acc.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.UserDomain;

public interface UserService extends BaseService<UserDomain> {
	public UserDomain getUser(UserDomain userDomain);

	public int checkUser(UserDomain userDomain);

	public int updateRate(UserDomain userDomain);
	
	public List<UserDomain> getAllCheckedUser();
	
	public List<UserDomain> findSubUserList(UserDomain userDomain);
}
