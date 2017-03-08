package sds.common.remote.domain;

import com.riozenc.quicktool.config.Global;

/**
 * 发送注册请求数据格式
 * 
 * @author riozenc
 *
 */
public class RegisterDomain {

	private String account;// 手机号码 必填
	private String pass = "123123";// >=6位(英文/数字) 必填
	private String code = Global.getConfig("CODE");// 按照下放信息填写 必填
	private String cbzid = Global.getConfig("CBZID");// 按照下放信息填写 必填

	public RegisterDomain(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPass() {
		return pass;
	}

	public String getCode() {
		return code;
	}

	public String getCbzid() {
		return cbzid;
	}

}
