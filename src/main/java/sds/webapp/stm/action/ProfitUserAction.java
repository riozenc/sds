package sds.webapp.stm.action;

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
import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.service.ProfitService;
import sds.webapp.stm.service.ProfitUserService;
import sds.webapp.stm.util.SettlementHandler;

/**
 * 代理商分润统计
 * 
 * @author riozenc
 *
 */
@ControllerAdvice
@RequestMapping("profitUser")

public class ProfitUserAction extends BaseAction {

	@Autowired
	@Qualifier("profitUserServiceImpl")
	private ProfitUserService profitUserService;

	@Autowired
	@Qualifier("profitServiceImpl")
	private ProfitService profitService;

	/**
	 * 更新数据
	 * 
	 * @param profitUserDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(ProfitUserDomain profitUserDomain) {
		int i = profitUserService.update(profitUserDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=findProfitUserByWhere")
	public String findProfitUserByWhere(ProfitUserDomain profitUserDomain) {
		List<ProfitUserDomain> list = profitUserService.findProfitUserByWhere(profitUserDomain);
		return JSONUtil.toJsonString(new JsonGrid(profitUserDomain, list));
	}

//	/**
//	 * 我的分润
//	 * 
//	 * @param profitUserDomain
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(params = "type=myProfitUser")
//	public String myProfitUser(ProfitUserDomain profitUserDomain) {
//
//		profitUserDomain.setAgentId(UserUtils.getPrincipal().getUserDomain().getId());// 只查询自己的分润
//		List<ProfitDomain> list = profitService.findProfitByUser(profitUserDomain);
//		List<ProfitUserDomain> profitUserDomains = SettlementHandler.computeProfitByUser(list);
//
//		return JSONUtil.toJsonString(new JsonGrid(profitUserDomain, profitUserDomains));
//	}

	/**
	 * 下级分润
	 * 
	 * @param profitUserDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=subProfitUser")
	public String subProfitUser(ProfitUserDomain profitUserDomain) {
		// parent_id
		profitUserDomain.setAgentId(UserUtils.getPrincipal().getUserDomain().getId());
		List<ProfitDomain> list = profitService.findSubProfitByUser(profitUserDomain);
		List<ProfitUserDomain> profitUserDomains = SettlementHandler.computeProfitByUser(list);
		return JSONUtil.toJsonString(new JsonGrid(profitUserDomain, profitUserDomains));
	}

	@ResponseBody
	@RequestMapping(params = "type=findDateProfitUserByWhere")
	public String findDateProfitUserByWhere(ProfitUserDomain profitUserDomain) {

		List<ProfitUserDomain> list = profitService.findDateProfitUserByWhere(profitUserDomain);

		// List<ProfitUserDomain> list =
		// profitUserService.findDateProfitUserByWhere(profitUserDomain);
		return JSONUtil.toJsonString(new JsonGrid(profitUserDomain, list));
	}

}
