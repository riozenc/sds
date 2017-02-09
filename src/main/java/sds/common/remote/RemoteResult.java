package sds.common.remote;

public class RemoteResult {
	private String respCode;
	private String respInfo;
	private String msg;// 返回结果信息
	private String privatekey;// 密钥
	private String QRcodeURL;// 二维码支付地址
	private String orderId;// 订单号

	public RemoteResult() {
	}

	public RemoteResult(String respCode, String respInfo) {
		this.respCode = respCode;
		this.respInfo = respInfo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getQRcodeURL() {
		return QRcodeURL;
	}

	public void setQRcodeURL(String qRcodeURL) {
		QRcodeURL = qRcodeURL;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
