/**
 * Title:TestHF.java
 * Author:riozenc
 * Datetime:2017年5月24日 上午9:32:25
**/
package sds.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.remote.Regesitor;
import sds.common.remote.RemoteHandler;
import sds.common.remote.RemoteResult;
import sds.common.remote.RemoteUtils;
import sds.common.remote.RemoteUtils.REMOTE_TYPE;
import sds.common.remote.util.Common;
import sds.webapp.acc.domain.MerchantDomain;

public class TestHF {

	// 注册
	public static void register() {
		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setAccount("19900000001");

		String result = Regesitor.sendPost(Common.REGISTERURL, "account=" + merchantDomain.getAccount() + "&pass="
				+ "123123" + "&code=" + Common.CODE + "&cbzid=" + Common.CBZID);

		RemoteResult remoteResult;
		try {
			remoteResult = JSONUtil.readValue(result, RemoteResult.class);
			System.out.println(JSONUtil.toJsonString(remoteResult));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 下载密钥
	public static void downloadKey() {
		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setAccount("19900000001");
		RemoteResult remoteResult = RemoteUtils.process(merchantDomain, REMOTE_TYPE.DOWNLOAD_KEY);
		System.out.println(JSONUtil.toJsonString(remoteResult));
	}

	// 验卡接口
	public static void validCard() {
		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setAccount("19900000001");

		RemoteResult remoteResult;
		try {
			remoteResult = RemoteHandler.validCard(merchantDomain);
			System.out.println(JSONUtil.toJsonString(remoteResult));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 修改费率

	// 二维码支付接口

	// 订单状态查询接口

	// 交易回调通知返回信息

	// 一码付接口

	// 商户审核回调通知返回信息

	public static void main(String[] args) {
		downloadKey();
	}
}
