/**
 * Title:WithdrawalsAction.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:30:33
**/
package sds.webapp.stm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
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

	/**
	 * 取现申请
	 * 
	 * @param amount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=withdrawals")
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

	/**
	 * 获取提现申请
	 * 
	 * @param withdrawalsDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findByWhere")
	public String findByWhere(WithdrawalsDomain withdrawalsDomain) {
		List<WithdrawalsDomain> withdrawalsDomains = null;
		if (UserUtils.hasRole("withdrawalsCheck")) {
			withdrawalsDomains = withdrawalsService.getwithdrawals(withdrawalsDomain);
		} else {
			withdrawalsDomains = new ArrayList<WithdrawalsDomain>();
		}
		return JSONUtil.toJsonString(new JsonGrid(withdrawalsDomain, withdrawalsDomains));
	}

	/**
	 * 提现同意
	 * 
	 * @param withdrawalsDomain
	 *            id,merchantId,amount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=agree")
	public String agree(WithdrawalsDomain withdrawalsDomain) {
		// 确认之后录入一笔
		withdrawalsDomain.setStatus(1);
		int i = withdrawalsService.agree(withdrawalsDomain);
		if (i > 1) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "操作成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "操作失败."));
		}
	}

	/**
	 * 提现驳回
	 * 
	 * @param withdrawalsDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=reject")
	public String reject(WithdrawalsDomain withdrawalsDomain) {
		// 确认之后录入一笔
		withdrawalsDomain.setStatus(2);
		int i = withdrawalsService.update(withdrawalsDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新失败."));
		}
	}

	/**
	 * 导出excel文件
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=export")
	public String export() {
		return null;
	}
}
