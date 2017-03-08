package sds.common.remote.domain;

public class DownloadKeyDomain {
	private String orderCode = "tb_DownLoadKey";
	private String account;
	private String password = "123123";
//	private String language = "Java";

	public DownloadKeyDomain(String account) {
		this.account = account;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

//	public String getLanguage() {
//		return language;
//	}

}
