package sds.common.remote.domain;

public class ScanPayDomain {
	private String tran_amount;// 交易金额 1..1 s..11 必填
	private String channel_code;// 支付通道 1.1 s.50 WXPAY：微信 ALIPAY：支付宝 UNIPAY:银联
								// 必填
	private String product_name;// 商品名称 1..1 s..50 必填
	private String product_detail;// 商品描述 1..1 s..50 非必填
	private String auth_code;// 支付授权码 1..1 s..50 必填 [扫码支付授权码]

	public String getTran_amount() {
		return tran_amount;
	}

	public void setTran_amount(String tran_amount) {
		this.tran_amount = tran_amount;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_detail() {
		return product_detail;
	}

	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

}
