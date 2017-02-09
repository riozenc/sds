package sds.webapp.ord.action;

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
import sds.common.remote.RemoteResult;
import sds.common.remote.RemoteUtils;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.ord.service.OrderService;

@ControllerAdvice
@RequestMapping("order")
public class OrderAction extends BaseAction {

	@Autowired
	@Qualifier("orderServiceImpl")
	private OrderService orderService;

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;

	/**
	 * 正扫支付
	 * 
	 * @param amount
	 * @param info
	 * @param channelCode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=pay")
	public String pay(double amount, String info, int channelCode) throws Exception {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		

		RemoteResult remoteResult = RemoteUtils.pay(merchantDomain, amount, info, channelCode);
		if (RemoteUtils.resultProcess(remoteResult)) {
			OrderDomain orderDomain = new OrderDomain();
			orderDomain.setOrderId(remoteResult.getOrderId());
			orderDomain.setChannelCode(channelCode);
			orderDomain.setAmount(amount / 100);
			orderDomain.setDate(new Date());
			orderDomain.setAccount(UserUtils.getPrincipal().getUserId());
			// orderDomain.setProxyAccount(
			// orderDomain.getAccount().equals(merchantDomain.getAccount()) ?
			// null : merchantDomain.getAccount());
			orderDomain.setProxyAccount(merchantDomain.getAccount());
			orderDomain.setCodeUrl(remoteResult.getQRcodeURL());
			orderDomain.setRemark(info);
			orderDomain.setStatus(0);// 0：未查询
			orderService.insert(orderDomain);
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
		}
		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "支付成功."));
	}

	/**
	 * 反扫支付
	 * 
	 * @param amount
	 * @param channelCode
	 * @param productName
	 * @param productDetail
	 * @param authCode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=scanPay")
	public String scanPay(double amount, int channelCode, String productName, String productDetail, String authCode)
			throws Exception {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		
		// authCode 付款码
		RemoteResult remoteResult = RemoteUtils.scanPay(merchantDomain, amount, channelCode, productName, productDetail,
				authCode);
		if (RemoteUtils.resultProcess(remoteResult)) {
			OrderDomain orderDomain = new OrderDomain();
			orderDomain.setOrderId(remoteResult.getOrderId());
			orderDomain.setChannelCode(channelCode);
			orderDomain.setAmount(amount / 100);
			orderDomain.setDate(new Date());
			orderDomain.setAccount(UserUtils.getPrincipal().getUserId());
			orderDomain.setProxyAccount(merchantDomain.getAccount());
			orderDomain.setCodeUrl(remoteResult.getQRcodeURL());
			orderDomain.setRemark(productDetail);
			orderDomain.setStatus(0);// 0：未查询
			orderService.insert(orderDomain);
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
		}
		return null;
	}

	/**
	 * 订单状态查询
	 * 
	 * @param orderDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=orderConfirm")
	public String orderConfirm(OrderDomain orderDomain) throws Exception {

		RemoteResult remoteResult = RemoteUtils.orderConfirm(orderDomain.getOrderId());
		if (RemoteUtils.resultProcess(remoteResult)) {
			// 更新
			orderDomain.setStatus(1);
		} else {
			orderDomain.setStatus(2);
		}
		orderDomain.setRespCode(remoteResult.getRespCode());
		orderDomain.setRespInfo(remoteResult.getRespInfo());
		orderService.update(orderDomain);
		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "查询成功."));
	}

	@ResponseBody
	@RequestMapping(params = "type=findOrder")
	public String findOrderByWhere(OrderDomain orderDomain) {

		List<OrderDomain> list = orderService.findByWhere(orderDomain);

		return JSONUtil.toJsonString(new JsonGrid(orderDomain, list));
	}
}
