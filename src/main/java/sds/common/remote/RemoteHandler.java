package sds.common.remote;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.riozenc.quicktool.common.util.json.JSONUtil;
import com.riozenc.quicktool.config.Global;

import sds.common.remote.domain.ChangeRateDomain;
import sds.common.remote.domain.DownloadKeyDomain;
import sds.common.remote.domain.OrderConfirmDomain;
import sds.common.remote.domain.PayDomain;
import sds.common.remote.domain.RegisterDomain;
import sds.common.remote.domain.ScanPayDomain;
import sds.common.remote.domain.VerifyCardDomain;
import sds.common.remote.util.Base64;
import sds.common.remote.util.Base64Utils;
import sds.common.remote.util.Common;
import sds.common.remote.util.DesUtil;
import sds.common.remote.util.LocalUtil;
import sds.common.remote.util.MyURLConnection;
import sds.common.remote.util.RSAUtils;
import sds.common.remote.util.SignUtil;
import sds.webapp.acc.domain.MerchantDomain;

public class RemoteHandler {

	/**
	 * 注册
	 * 
	 * @param account
	 * @param password暂时无用
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	static RemoteResult register(final RegisterDomain registerDomain)
			throws JsonParseException, JsonMappingException, IOException {

		String result = Regesitor.sendPost(Common.REGISTERURL,
				"account=" + registerDomain.getAccount() + "&pass=" + registerDomain.getPass() + "&code="
						+ registerDomain.getCode() + "&cbzid=" + registerDomain.getCbzid());

		RemoteResult remoteResult = JSONUtil.readValue(result, RemoteResult.class);
		return remoteResult;
	}

	/**
	 * 下载密钥
	 * 
	 * @param account
	 * @param password暂时无用
	 * @return
	 */
	static RemoteResult downLoadKey(final DownloadKeyDomain downloadKeyDomain) {

		try {
			String datas = Base64.encodeToString(JSONUtil.toJsonString(downloadKeyDomain));
			// 加密
			PublicKey publicKey = RSAUtils.loadPublicKey(Common.PUBLICKKEY);
			byte[] decryptByte1 = RSAUtils.encryptData(datas.getBytes(), publicKey);

			Map<String, String> result = new HashMap<String, String>();
			result.put("data", Base64Utils.encode(decryptByte1));
			String request = JSONUtil.toJsonString(result);

			// 发送数据
			byte[] res = MyURLConnection.postBinResource(Common.URL, request, Common.CHARSET, 30);
			String response = new String(res);

			try {
				RemoteBlackResult remoteBlackResult = JSONUtil.readValue(response, RemoteBlackResult.class);

				int count = Integer.valueOf(remoteBlackResult.getCount());
				String respData = remoteBlackResult.getData();

				String newString = new String(
						DesUtil.des3Decrypt(SignUtil.hexStrToBytes(Common.KEY), SignUtil.hexStrToBytes(respData)));

				respData = newString.substring(0, count);

				RemoteResult remoteResult = JSONUtil.readValue(respData, RemoteResult.class);
				return remoteResult;
			} catch (Exception e) {
				return JSONUtil.readValue(response, RemoteResult.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new RemoteResult("999999", e.getMessage() == null ? e.getClass().getName() : e.getMessage());
		}
	}

	/**
	 * 验卡
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	public static RemoteResult validCard(final MerchantDomain merchantDomain) throws Exception {
		VerifyCardDomain verifyCardDomain = new VerifyCardDomain();
		verifyCardDomain.setCmer(Base64.encodeToString(merchantDomain.getCmer()));
		verifyCardDomain.setCmer_sort(Base64.encodeToString(merchantDomain.getCmerSort()));
		verifyCardDomain.setPhone(merchantDomain.getPhone() == null ? "" : merchantDomain.getPhone());
		verifyCardDomain.setBusiness_id(merchantDomain.getBusinessId());
		verifyCardDomain.setChannel_code("WXPAY");
		verifyCardDomain.setCard_type("1");
		verifyCardDomain.setCert_type("00");
		verifyCardDomain.setLocation(
				Base64.encodeToString(merchantDomain.getLocation() == null ? "" : merchantDomain.getLocation()));// 开户城市
		verifyCardDomain.setCert_correct("");
		verifyCardDomain.setCert_opposite("");
		verifyCardDomain.setCert_meet("");
		verifyCardDomain.setCard_correct("");
		verifyCardDomain.setCard_opposite("");

		// 结算卡四要素
		verifyCardDomain.setReal_name(Base64.encodeToString(merchantDomain.getRealName()));
		verifyCardDomain.setCard_no(merchantDomain.getCardNo());
		verifyCardDomain.setCert_no(merchantDomain.getCertNo());// 证件号
		verifyCardDomain.setMobile(merchantDomain.getMobile());// 手机号

		return encryptionProcess("tb_verifyInfo", merchantDomain.getAccount(), merchantDomain.getPrivatekey(),
				verifyCardDomain, false);

	}

	/**
	 * 修改费率
	 */
	static RemoteResult changeRate(MerchantDomain merchantDomain) throws Exception {

		String privateKey = merchantDomain.getPrivatekey();

		ChangeRateDomain changeRateDomain = new ChangeRateDomain();
		changeRateDomain.setWx_rate(formatDouble(merchantDomain.getWxRate()));
		changeRateDomain.setAli_rate(formatDouble(merchantDomain.getAliRate()));
		return encryptionProcess("xy_ChangeRate", merchantDomain.getAccount(), privateKey, changeRateDomain, true);
	}

	/**
	 * 查询订单状态
	 * 
	 * @param merchantDomain
	 * @param orderId
	 * @return
	 */
	static RemoteResult orderConfirm(String account, String privateKey, String orderId) throws Exception {
		OrderConfirmDomain orderConfirmDomain = new OrderConfirmDomain();
		orderConfirmDomain.setOrderId(orderId);
		return encryptionProcess("tb_OrderConfirm", account, privateKey, orderConfirmDomain, false);
	}

	/**
	 * 支付
	 * 
	 * @param merchantDomain
	 * @param amount
	 * @param channelCode
	 * @param info
	 * @return
	 * @throws Exception
	 */
	static RemoteResult pay(String account, String privateKey, int amount, int channelCode, String info)
			throws Exception {

		PayDomain payDomain = new PayDomain();
		payDomain.setAmount(Integer.toString(amount));
		payDomain.setChannel_code(chooseChannel(channelCode));
		payDomain.setInfo(info);

		return encryptionProcess("tb_WeixinPay", account, privateKey, payDomain, true);
	}

	/**
	 * 反扫支付
	 * 
	 * @param merchantDomain
	 * @param amount
	 * @param channelCode
	 * @param productName
	 * @param productDetail
	 * @param authCode
	 * @return
	 * @throws Exception
	 */
	public static RemoteResult scanPay(String account, String privateKey, int amount, int channelCode,
			String productName, String productDetail, String authCode) throws Exception {

		ScanPayDomain scanPayDomain = new ScanPayDomain();
		scanPayDomain.setTran_amount(Integer.toString(amount));
		scanPayDomain.setChannel_code(chooseChannel(channelCode));
		scanPayDomain.setProduct_name(productName);
		scanPayDomain.setProduct_detail(productDetail);
		scanPayDomain.setAuth_code(authCode);
		return encryptionProcess("tb_wxscanpay", account, privateKey, scanPayDomain, true);
	}

	/**
	 * 一码付
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static RemoteResult getACodePay(String account, String privateKey) throws Exception {

		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("orderCode", "tb_GetACodePay");
		jsonMap.put("account", account);// 手机号
		jsonMap.put("password", "123123");

		Map<String, String> msgMap = new HashMap<>();
		msgMap.put("cbzid", Global.getConfig("CBZID"));

		String msgJson = JSONUtil.toJsonString(msgMap);

		// 签名
		byte[] sign = LocalUtil.sign(Base64.decode(privateKey.getBytes()), msgJson);

		jsonMap.put("msg", JSONUtil.toJsonString(msgMap));
		String data = Base64.encodeToString(JSONUtil.toJsonString(jsonMap));
		// 加密
		PublicKey publicKey = RSAUtils.loadPublicKey(Common.PUBLICKKEY);
		byte[] decryptByte1 = RSAUtils.encryptData(data.getBytes(), publicKey);

		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("signature", new String(sign));
		dataMap.put("data", Base64Utils.encode(decryptByte1));
		dataMap.put("pass", "1");

		String request = JSONUtil.toJsonString(dataMap);

		byte[] res = MyURLConnection.postBinResource(Common.URL, request, Common.CHARSET, 30);
		String response = new String(res, "UTF-8");

		// 可能返回异常，直接返回RemoteResult
		try {
			RemoteBlackResult remoteBlackResult = JSONUtil.readValue(response, RemoteBlackResult.class);

			String msg = remoteBlackResult.getData();
			String signature = remoteBlackResult.getSignature();
			PrivateKey key = RSAUtils.loadPrivateKey(privateKey);

			byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(msg), key);
			String decryptStr = new String(decryptByte);
			String datas = Base64.decodeToString(decryptStr);
			RemoteResult msgResult = JSONUtil.readValue(datas, RemoteResult.class);

			String msgDatas = msgResult.getMsg();
			// 验签
			boolean vfy = LocalUtil.verifySignature(Base64.decode(Common.PUBLICKKEY.getBytes()), signature,
					msgDatas.getBytes(Common.CHARSET));
			if (vfy) {
				RemoteResult remoteResult = JSONUtil.readValue(msgDatas, RemoteResult.class);
				return remoteResult;
			} else {
				return new RemoteResult("999999", "异常数据.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONUtil.readValue(response, RemoteResult.class);
		}
	}

	public static RemoteResult encryptionProcess(String orderCode, String account, String privateKey, Object params,
			boolean isEncode) throws Exception {

		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("orderCode", orderCode);
		jsonMap.put("account", account);// 手机号

		String paramsJson = null;

		if (isEncode) {
			paramsJson = Base64.encodeToString(JSONUtil.toJsonString(params));// 与验卡不同
		} else {
			paramsJson = JSONUtil.toJsonString(params);
		}

		byte[] sign = LocalUtil.sign(Base64.decode(privateKey.getBytes()), paramsJson);
		if (sign == null) {
			throw new Exception("签名失败.");
		}
		jsonMap.put("msg", paramsJson);

		String data = Base64.encodeToString(JSONUtil.toJsonString(jsonMap));
		// 加密
		PublicKey publicKey = RSAUtils.loadPublicKey(Common.PUBLICKKEY);
		byte[] decryptByte1 = RSAUtils.encryptData(data.getBytes(), publicKey);

		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("signature", new String(sign));
		dataMap.put("data", Base64Utils.encode(decryptByte1));

		String request = JSONUtil.toJsonString(dataMap);
		byte[] res = MyURLConnection.postBinResource(Common.URL, request, Common.CHARSET, 30);
		String response = new String(res, "UTF-8");

		// 可能返回异常，直接返回RemoteResult
		try {
			RemoteBlackResult remoteBlackResult = JSONUtil.readValue(response, RemoteBlackResult.class);

			String msg = remoteBlackResult.getData();
			String signature = remoteBlackResult.getSignature();
			PrivateKey key = RSAUtils.loadPrivateKey(privateKey);

			byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(msg), key);
			String decryptStr = new String(decryptByte);
			String datas = Base64.decodeToString(decryptStr);
			RemoteResult msgResult = JSONUtil.readValue(datas, RemoteResult.class);

			String msgDatas = msgResult.getMsg();
			// 验签
			boolean vfy = LocalUtil.verifySignature(Base64.decode(Common.PUBLICKKEY.getBytes()), signature,
					msgDatas.getBytes(Common.CHARSET));
			if (vfy) {
				RemoteResult remoteResult = JSONUtil.readValue(msgDatas, RemoteResult.class);

				return remoteResult;
			} else {
				return new RemoteResult("999999", "异常数据.");
			}
		} catch (Exception e) {
			e.printStackTrace();

			return JSONUtil.readValue(response, RemoteResult.class);
		}

	}

	// 格式化浮点类型为字符串。
	private static String formatDouble(Double value) {
		if (value == null) {
			return "0";
		}
		return Double.toString(value);
	}

	// 支付通道选择
	private static String chooseChannel(int channelCode) {
		return channelCode == 1 ? "WXPAY" : "ALIPAY";
	}
}
