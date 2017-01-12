package sds.webapp.ord.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;

@ControllerAdvice
@RequestMapping("/order")
public class OrderAction extends BaseAction {

	@Autowired
	@Qualifier("orderServiceImpl")
	private OrderService orderService;

	// 正扫
	public String pay(OrderDomain orderDomain) {
		return null;
	}

	// 反扫
	public String ScanPay(OrderDomain orderDomain, String authCode) {
		// authCode 付款码

		return null;
	}

	@ResponseBody
	@RequestMapping(params = "type=findOrder")
	public String findOrderByWhere(OrderDomain orderDomain) {

		List<OrderDomain> list = orderService.findByWhere(orderDomain);

		return JSONUtil.toJsonString(new JsonGrid(orderDomain, list));
	}
}
