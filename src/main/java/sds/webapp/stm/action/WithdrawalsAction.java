/**
 * Title:WithdrawalsAction.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:30:33
**/
package sds.webapp.stm.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonResult;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.stm.domain.WithdrawalsDomain;
import sds.webapp.stm.service.WithdrawalsService;

/**
 * 提现
 * 
 * @author riozenc
 *
 */
@ControllerAdvice
@RequestMapping("withdrawals")
public class WithdrawalsAction extends BaseAction {

	@Autowired
	@Qualifier("withdrawalsServiceImpl")
	private WithdrawalsService withdrawalsService;

	public String withdrawals(Double amount) {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();

		WithdrawalsDomain withdrawalsDomain = new WithdrawalsDomain();
		withdrawalsDomain.setAmount(amount);
		withdrawalsDomain.setDate(new Date());
		withdrawalsDomain.setMerchantId(merchantDomain.getId());
		withdrawalsDomain.setStatus(0);
		withdrawalsService.insert(withdrawalsDomain);

		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "提现申请已记录."));
	}
}
