/**
 * Title:WithdrawalsService.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:28:13
**/
package sds.webapp.stm.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.stm.domain.WithdrawalsDomain;

public interface WithdrawalsService extends BaseService<WithdrawalsDomain> {

	public int agree(WithdrawalsDomain withdrawalsDomain);
	
	public List<WithdrawalsDomain> getwithdrawals(WithdrawalsDomain withdrawalsDomain);
}
