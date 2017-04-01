package sds.webapp.ord.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.common.util.json.JSONUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.remote.RemoteResult;
import sds.common.remote.RemoteUtils;
import sds.common.remote.util.Base64Utils;
import sds.common.remote.util.RSAUtils;
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

		if (merchantDomain.getCodePayUrl() == null) {
			// MerchantDomain merchantDomain = new MerchantDomain();
			// merchantDomain.setId(40);

			MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);
			RemoteResult remoteResult = RemoteUtils.getACodePay(vm);
			if (RemoteUtils.resultProcess(remoteResult)) {
				merchantDomain.setCodePayUrl(remoteResult.getCodePayUrl());
				merchantService.update(merchantDomain);
				return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, remoteResult.getCodePayUrl()));
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR,
						remoteResult.getMsg() + "[" + remoteResult.getRespInfo() + "]"));
			}
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, merchantDomain.getCodePayUrl()));
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

		LogUtil.getLogger(LOG_TYPE.OTHER).info(JSONUtil.toJsonString(orderDomain) + "   [" + WXOrderNo + "]");

		String orderId = orderDomain.getOrderId();

		if (orderId.length() > 100) {
			// 一码付订单生成

			Map<String, String> map = merchantService.getRAandVP(orderDomain.getAccount());

			PrivateKey privateKey;
			try {
				privateKey = RSAUtils.loadPrivateKey(map.get("privatekey"));
				String decryptStr = orderDomain.getOrderId().replace("AAAAAA", "+");
				byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(decryptStr), privateKey);
				orderDomain.setOrderId(new String(decryptByte));
				LogUtil.getLogger(LOG_TYPE.OTHER).info("解密成功,生成" + orderDomain.getOrderId() + "柜台码订单.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.getLogger(LOG_TYPE.ERROR)
						.info(orderDomain.getAccount() + "[" + map.get("privatekey") + "]异常,无法正常使用..");
				orderDomain.setOrderId(orderDomain.getOrderId());
			}
			orderDomain.setDate(new Date());
			orderDomain.setProxyAccount(orderDomain.getAccount());
			orderDomain.setAccount(map.get("account"));
			orderDomain.setRemark("柜台码支付");
			orderDomain.setStatus(0);
			orderService.insert(orderDomain);
		} else {
			BigDecimal amount = new BigDecimal(Double.toString(orderDomain.getAmount()));
			orderDomain.setAmount(amount.divide(new BigDecimal(100), 2, RoundingMode.DOWN).doubleValue());

			if ("000000".equals(orderDomain.getRespCode())) {
				orderDomain.setStatus(1);

				Map<String, String> map = merchantService.getRAandVP(orderDomain.getAccount());
				orderDomain.setAccount(map.get("account"));

				if (profitService.profit(orderDomain) > 0) {
					LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）交易成功,分润成功[" + WXOrderNo
							+ "]" + DateUtil.formatDate(new Date()));
				} else {
					LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）交易成功,分润失败[" + WXOrderNo
							+ "]" + DateUtil.formatDate(new Date()));
				}
				return;
			}

			try {
				Thread.sleep(2 * 1000);

				OrderDomain order = orderService.findByKey(orderDomain);
				orderDomain.setDate(order.getDate());

				Map<String, String> map = merchantService.getRAandVP(orderDomain.getAccount());
				MerchantDomain vm = new MerchantDomain();
				vm.setAccount(orderDomain.getAccount());
				vm.setPrivatekey(map.get("privatekey"));
				orderDomain.setAccount(map.get("account"));

				RemoteResult remoteResult = RemoteUtils.orderConfirm(vm, orderDomain.getOrderId());
				if (RemoteUtils.resultProcess(remoteResult)) {
					// 更新
					orderDomain.setStatus(1);
					orderDomain.setOrderNo(WXOrderNo);
					orderDomain.setRespCode(remoteResult.getRespCode());
					orderDomain.setRespInfo(remoteResult.getRespInfo());

					if (profitService.profit(orderDomain) > 0) {
						LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）交易成功,分润成功[" + WXOrderNo
								+ "]" + DateUtil.formatDate(new Date()));
					} else {
						LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）交易成功,分润失败[" + WXOrderNo
								+ "]" + DateUtil.formatDate(new Date()));
					}
				} else {
					orderDomain.setStatus(2);
					orderDomain.setOrderNo(WXOrderNo);
					orderDomain.setRespCode(remoteResult.getRespCode());
					orderDomain.setRespInfo(remoteResult.getRespInfo());
					orderService.update(orderDomain);
					LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）暂未交易[" + WXOrderNo + "]"
							+ DateUtil.formatDate(new Date()));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
		String msg = "";
		int code = 0;
		if (RemoteUtils.resultProcess(remoteResult)) {
			// 更新
			msg = msg + "交易成功";
			code = 200;
			LogUtil.getLogger(LOG_TYPE.OTHER)
					.info(orderDomain.getOrderId() + "（主动查询）交易成功[" + "]" + DateUtil.formatDate(new Date()));
		} else {
			orderDomain.setStatus(2);
			msg = msg + remoteResult.getRespInfo();
			code = 300;
			LogUtil.getLogger(LOG_TYPE.OTHER)
					.info(orderDomain.getOrderId() + "（主动查询）交易成功[" + "]" + DateUtil.formatDate(new Date()));
		}
		orderDomain.setRespCode(remoteResult.getRespCode());
		orderDomain.setRespInfo(remoteResult.getRespInfo());
		orderService.update(orderDomain);
		return JSONUtil.toJsonString(new JsonResult(code, orderDomain));
	}

	/**
	 * 获取订单
	 * 
	 * @param orderDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findOrder")
	public String getOrderByUser(OrderDomain orderDomain) {
		orderDomain.setId(UserUtils.getPrincipal().getId());
		List<OrderDomain> list = orderService.getOrderByUser(orderDomain);
		return JSONUtil.toJsonString(new JsonGrid(orderDomain, list));
	}

	/**
	 * 总收款
	 * 
	 * @param orderDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=getTotalAmountByOrder")
	public String getTotalAmountByOrder(OrderDomain orderDomain) {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		orderDomain.setAccount(merchantDomain.getAccount());
		return JSONUtil
				.toJsonString(new JsonResult(JsonResult.SUCCESS, orderService.getTotalAmountByOrder(orderDomain)));

	}

	/**
	 * 购卡接口（转发数据）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=purchasingCardCallback")
	public void purchasingCardCallback(OrderDomain orderDomain) {
		orderDomain.setStatus(1);
		if (profitService.profitQuick(orderDomain) > 0) {
			LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）交易成功,分润成功["
					+ orderDomain.getOrderId() + "]" + DateUtil.formatDate(new Date()));
		} else {
			LogUtil.getLogger(LOG_TYPE.OTHER).info(orderDomain.getOrderId() + "（回调查询）交易成功,分润失败["
					+ orderDomain.getOrderId() + "]" + DateUtil.formatDate(new Date()));
		}

	}

	@ResponseBody
	@RequestMapping(params = "type=purchasingCardCallbackTest")
	public void purchasingCardCallbackTest(HttpServletRequest httpServletRequest) {
		System.out.println(JSONUtil.toJsonString(httpServletRequest.getParameterMap()));
	}

	// // 批量报备商户+柜台码支付接口（）
	// @RequestMapping(params = "type=t")
	// public ModelAndView t(MerchantDomain merchantDomain, String nsukey,
	// HttpServletRequest httpServletRequest,
	// HttpServletResponse httpServletResponse) {
	// System.out.println(JSONUtil.toJsonString(httpServletRequest.getParameterMap()));
	//
	// WxNsukeyCache.putNsukey(nsukey);
	// ModelAndView view = new ModelAndView();
	// if (WxNsukeyCache.getValue(nsukey)) {
	//
	// if (merchantDomain.getId() == 1) {
	// view.setViewName(
	// "redirect:http://mf.branding.chinavalleytech.com/ChannelConn/ACodePay?data=e4fc9d080525c8ff58df50c3fcbff8793bfbeb80ca660a2dd55571263828e7a741a67f05ba23fefa9907476e17d423f7a251a55bb46228675e78d2f3b07b6f2e");
	// } else {
	// view.setViewName("wap_register_2017.html");
	// }
	// WxNsukeyCache.removeKey(nsukey);
	// return view;
	// } else {
	// try {
	// httpServletResponse.getWriter().write("success");
	// httpServletResponse.getWriter().close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return view;
	// }
	// }
	//
	// @ResponseBody
	// @RequestMapping(params = "type=getWxCache")
	// public String getWxCache() {
	//
	// return JSONUtil.toJsonString(WxNsukeyCache.get());
	// }
}
