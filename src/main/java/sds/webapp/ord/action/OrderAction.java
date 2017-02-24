package sds.webapp.ord.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

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
import sds.webapp.stm.service.ProfitService;

@ControllerAdvice
@RequestMapping("order")
public class OrderAction extends BaseAction {

	@Autowired
	@Qualifier("orderServiceImpl")
	private OrderService orderService;

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;

	@Autowired
	@Qualifier("profitServiceImpl")
	private ProfitService profitService;

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
	public String pay(int amount, String info, int channelCode) throws Exception {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);

		RemoteResult remoteResult = RemoteUtils.pay(vm, amount, info, channelCode);
		if (RemoteUtils.resultProcess(remoteResult)) {
			OrderDomain orderDomain = new OrderDomain();
			orderDomain.setOrderId(remoteResult.getOrderId());
			orderDomain.setChannelCode(channelCode);
			orderDomain.setAmount((double) amount / 100);
			orderDomain.setDate(new Date());
			orderDomain.setAccount(UserUtils.getPrincipal().getUserId());
			orderDomain.setProxyAccount(vm.getAccount());
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
		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, JSONUtil.toJsonString(remoteResult)));
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
	public String scanPay(int amount, int channelCode, String productName, String productDetail, String authCode)
			throws Exception {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);
		// authCode 付款码
		RemoteResult remoteResult = RemoteUtils.scanPay(vm, amount, channelCode, productName, productDetail, authCode);
		if (RemoteUtils.resultProcess(remoteResult)) {
			OrderDomain orderDomain = new OrderDomain();
			orderDomain.setOrderId(remoteResult.getOrderId());
			orderDomain.setChannelCode(channelCode);
			orderDomain.setAmount((double) amount / 100);
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
	 * 一码付（柜台码）
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=codePay")
	public String codePay() throws Exception {

		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		// MerchantDomain merchantDomain = new MerchantDomain();
		// merchantDomain.setId(40);

		MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);

		RemoteResult remoteResult = RemoteUtils.getACodePay(vm);

		if (RemoteUtils.resultProcess(remoteResult)) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, remoteResult.getCodePayUrl()));
		} else {
			return JSONUtil.toJsonString(
					new JsonResult(JsonResult.ERROR, remoteResult.getMsg() + "[" + remoteResult.getRespInfo() + "]"));
		}
	}

	/**
	 * 订单支付成功后回调
	 * 
	 * @param orderId
	 * @param respCode
	 * @param respInfo
	 * @param amount
	 * @param WXOrderNo
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=orderConfirmCallback")
	public void orderConfirmCallback(OrderDomain orderDomain, String WXOrderNo) {

		try {
			Thread.sleep(2 * 1000L);
			MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
			MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);

			RemoteResult remoteResult = RemoteUtils.orderConfirm(vm, orderDomain.getOrderId());
			if (RemoteUtils.resultProcess(remoteResult)) {
				// 更新
				orderDomain.setStatus(1);
			} else {
				orderDomain.setStatus(2);
			}
			orderDomain.setOrderNo(WXOrderNo);
			orderDomain.setRespCode(remoteResult.getRespCode());
			orderDomain.setRespInfo(remoteResult.getRespInfo());
			orderService.update(orderDomain);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(profitService.profit(orderDomain));
		// 日志记录
		LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "交易成功[" + WXOrderNo + "]");
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
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);

		RemoteResult remoteResult = RemoteUtils.orderConfirm(vm, orderDomain.getOrderId());
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
