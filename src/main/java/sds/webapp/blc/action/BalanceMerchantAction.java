/**
 * Title:BalanceMerchantAction.java
 * Author:riozenc
 * Datetime:2017年3月7日 下午3:45:16
**/
package sds.webapp.blc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import sds.webapp.blc.domain.BalanceMerchantDomain;
import sds.webapp.blc.domain.BalanceMerchantLogDomain;
import sds.webapp.blc.service.BalanceMerchantLogService;
import sds.webapp.blc.service.BalanceMerchantService;

@ControllerAdvice
@RequestMapping("balance")

public class BalanceMerchantAction extends BaseAction {

	@Autowired
	@Qualifier("balanceMerchantServiceImpl")
	private BalanceMerchantService balanceMerchantService;

	@Autowired
	@Qualifier("balanceMerchantLogServiceImpl")
	private BalanceMerchantLogService balanceMerchantLogService;

	@ResponseBody
	@RequestMapping(params = "type=getBalanceByMerchant")
	public String getBalanceByMerchant() {
		BalanceMerchantDomain balanceMerchantDomain = new BalanceMerchantDomain();
		balanceMerchantDomain.setId(UserUtils.getPrincipal().getMerchantDomain().getId());
		balanceMerchantDomain.setAccount(UserUtils.getPrincipal().getMerchantDomain().getAccount());
		balanceMerchantDomain = balanceMerchantService.findByKey(balanceMerchantDomain);
		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, balanceMerchantDomain));
	}

	@ResponseBody
	@RequestMapping(params = "type=getCountBalance")
	public String getCountBalance() {

		Map<String, String> map = new HashMap<String, String>();

		BalanceMerchantDomain balanceMerchantDomain = new BalanceMerchantDomain();
		balanceMerchantDomain.setId(UserUtils.getPrincipal().getMerchantDomain().getId());
		balanceMerchantDomain.setAccount(UserUtils.getPrincipal().getMerchantDomain().getAccount());
		balanceMerchantDomain = balanceMerchantService.findByKey(balanceMerchantDomain);
		if (balanceMerchantDomain == null) {
			map.put("profit", "0");
			map.put("total", "0");
		} else {
			map.put("profit", balanceMerchantDomain.getBalance().toString());
			map.put("total",
					balanceMerchantDomain.getCountIn() == null ? "0" : balanceMerchantDomain.getCountIn().toString());
		}

		// getCountBalanceByIn sql 未写
		// BalanceMerchantLogDomain balanceMerchantLogDomain = new
		// BalanceMerchantLogDomain();
		// balanceMerchantLogDomain.setAccount(UserUtils.getPrincipal().getMerchantDomain().getAccount());
		// String count =
		// balanceMerchantLogService.getCountBalanceByIn(balanceMerchantLogDomain);
		// map.put("total", count == null ? "0" : count);

		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, map));
	}

	@ResponseBody
	@RequestMapping(params = "type=getBalanceLogByMerchant")
	public String getBalanceLogByMerchant(BalanceMerchantLogDomain balanceMerchantLogDomain) {
		balanceMerchantLogDomain.setTargetId(UserUtils.getPrincipal().getMerchantDomain().getId());
		// balanceMerchantLogDomain.setId(UserUtils.getPrincipal().getMerchantDomain().getId());
		balanceMerchantLogDomain.setAccount(UserUtils.getPrincipal().getMerchantDomain().getAccount());
		List<BalanceMerchantLogDomain> list = balanceMerchantLogService.findByWhere(balanceMerchantLogDomain);
		return JSONUtil.toJsonString(new JsonGrid(balanceMerchantLogDomain, list));
	}

	@ResponseBody
	@RequestMapping(params = "type=getBalanceLogByUser")
	public String getBalanceLogByUser(BalanceMerchantLogDomain balanceMerchantLogDomain) {

		List<BalanceMerchantLogDomain> list = balanceMerchantLogService
				.getBalanceLogByUser(UserUtils.getPrincipal().getUserDomain());

		return JSONUtil.toJsonString(new JsonGrid(balanceMerchantLogDomain, list));
	}
}
