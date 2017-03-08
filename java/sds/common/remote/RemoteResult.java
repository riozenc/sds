package sds.common.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteResult {
	private String respCode;
	private String respInfo;
	private String msg;// 返回结果信息
	private String privatekey;// 密钥
	@JsonProperty("QRcodeURL")
	private String QRcodeURL;// 二维码支付地址

	@JsonProperty("QRCodeURL")
	private String codePayUrl;// 二维码支付地址

	private String orderId;// 订单号

	private boolean msgResult;// 垃圾属性，中谷系统垃圾
	private String sendFlag;// 垃圾属性，中谷系统垃圾

	public RemoteResult() {
	}

	public RemoteResult(String respCode, String respInfo) {
		this.respCode = respCode;
		this.respInfo = respInfo;
	}

	public String getCodePayUrl() {
		return codePayUrl;
	}

	public void setCodePayUrl(String codePayUrl) {
		this.codePayUrl = codePayUrl;
	}

	public String getQRcodeURL() {
		return QRcodeURL;
	}

	public void setQRcodeURL(String QRcodeURL) {
		this.QRcodeURL = QRcodeURL;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isMsgResult() {
		return msgResult;
	}

	public void setMsgResult(boolean msgResult) {
		this.msgResult = msgResult;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

}
