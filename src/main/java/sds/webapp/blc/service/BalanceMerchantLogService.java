/**
 * Title:BalanceMerchantLogService.java
 * Author:riozenc
 * Datetime:2017年3月7日 下午4:15:22
**/
package sds.webapp.blc.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.blc.domain.BalanceMerchantLogDomain;

public interface BalanceMerchantLogService extends BaseService<BalanceMerchantLogDomain> {

	public String getCountBalanceByIn(BalanceMerchantLogDomain balanceMerchantLogDomain);
	
	public List<BalanceMerchantLogDomain> getBalanceLogByUser(UserDomain userDomain);
}
