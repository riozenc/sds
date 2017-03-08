package sds.common.remote.domain;

import com.riozenc.quicktool.config.Global;

public class ChangeRateDomain {
	private String password = "123123";// 密码 1..1 s..11 必填
	private String wx_rate;// 微信签约费率 1..1 s..5 必填[例如：0.005]千分比
	private String ali_rate;// 支付宝签约费率 1..1 s..5 同微信费率(两者必填一个或2个都填)
	private String cbzid = Global.getConfig("CBZID");// 下发商户标识 1..1 s..11 必填
	private String channel_code = "WXPAY";// 支付通道 1.1 s.50 WXPAY：微信 ALIPAY：支付宝
											// UNIPAY:银联 必填 默认写死就好

	public String getPassword() {
		return password;
	}

	public String getWx_rate() {
		return wx_rate;
	}

	public void setWx_rate(String wx_rate) {
		this.wx_rate = wx_rate;
	}

	public String getAli_rate() {
		return ali_rate;
	}

	public void setAli_rate(String ali_rate) {
		this.ali_rate = ali_rate;
	}

	public String getCbzid() {
		return cbzid;
	}

	public String getChannel_code() {
		return channel_code;
	}

}
