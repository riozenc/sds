package sds.webapp.acc.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.service.MerchantService;

@ControllerAdvice
@RequestMapping("merchant")
public class MerchantAction extends BaseAction {

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;

	/**
	 * 新增商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=insert")
	public String insert(MerchantDomain merchantDomain) {
		int i = merchantService.insert(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "新增商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "新增商户失败"));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=delete")
	public String delete(MerchantDomain merchantDomain) {
		int i = merchantService.delete(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "删除商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "删除商户失败"));
		}
	}

	/**
	 * 修改商户信息
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(MerchantDomain merchantDomain) {
		int i = merchantService.update(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新商户失败"));
		}
	}

	/**
	 * 根据条件查询商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findMerchantByWhere")
	public String findMerchantByWhere(MerchantDomain merchantDomain) {
		List<MerchantDomain> list = merchantService.findByWhere(merchantDomain);
		return JSONUtil.toJsonString(new JsonGrid(merchantDomain, list));
	}

	/**
	 * 修改商户费率
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=updateRate")
	public String updateRate(MerchantDomain merchantDomain) {
		int i = merchantService.updateRate(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新商户失败"));
		}
	}
}
