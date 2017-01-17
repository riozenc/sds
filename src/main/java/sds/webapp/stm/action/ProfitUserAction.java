package sds.webapp.stm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.service.ProfitUserService;

/**
 * 代理商分润统计
 * 
 * @author riozenc
 *
 */
public class ProfitUserAction extends BaseAction {

	@Autowired
	@Qualifier("profitServiceImpl")
	private ProfitUserService profitUserService;

	@ResponseBody
	@RequestMapping(params = "type=count")
	public String count(ProfitUserDomain profitUserDomain) {
		return null;
	}

	@ResponseBody
	@RequestMapping(params = "type=findProfitUserByWhere")
	public String findProfitUserByWhere(ProfitUserDomain profitUserDomain) {

		List<ProfitUserDomain> list = profitUserService.findByWhere(profitUserDomain);

		return JSONUtil.toJsonString(new JsonGrid(profitUserDomain, list));
	}
}
