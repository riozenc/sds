package sds.webapp.stm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.stm.domain.ProfitMerchantDomain;
import sds.webapp.stm.service.ProfitMerchantService;

/**
 * 商户分润统计
 * 
 * @author riozenc
 *
 */

@ControllerAdvice
@RequestMapping("profitMerchant")

public class ProfitMerchantAction extends BaseAction {

	@Autowired
	@Qualifier("profitMerchantServiceImpl")
	private ProfitMerchantService profitMerchantService;

//	/**
//	 * 获取收益额 status=0 收款 status=1取现
//	 * 
//	 * @param profitMerchantDomain
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(params = "type=count")
//	public String count() {
//		ProfitMerchantDomain profitMerchantDomain = new ProfitMerchantDomain();
//		profitMerchantDomain.setMerchantId(UserUtils.getPrincipal().getMerchantDomain().getId());
//
//		String profit = profitMerchantService.getMerchantTotalProfit(profitMerchantDomain);
//
//		profitMerchantDomain.setStatus(0);
//		String total = profitMerchantService.getMerchantTotalProfit(profitMerchantDomain);
//
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("total", total == null ? "0" : total);
//		map.put("profit", profit == null ? "0" : profit);
//
//		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, map));
//	}

	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(ProfitMerchantDomain profitMerchantDomain) {
		int i = profitMerchantService.update(profitMerchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=findProfitMerchantByWhere")
	public String findProfitMerchantByWhere(ProfitMerchantDomain profitMerchantDomain) {

		List<ProfitMerchantDomain> list = profitMerchantService.findByWhere(profitMerchantDomain);

		return JSONUtil.toJsonString(new JsonGrid(profitMerchantDomain, list));
	}

}
