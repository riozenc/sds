package sds.webapp.ord.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;

@ControllerAdvice
@RequestMapping("/order")
public class OrderAction {

	@Autowired
	@Qualifier("orderServiceImpl")
	private OrderService orderService;

	@RequestMapping(params = "type=findOrder")
	public String findOrderByWhere(OrderDomain orderDomain) {

		List<OrderDomain> list = orderService.findByWhere(orderDomain);

		return JSONUtil.getJsonResult(new JsonGrid(list.size(), 10, list));
	}
}
