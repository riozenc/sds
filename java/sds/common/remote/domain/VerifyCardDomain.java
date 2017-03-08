package sds.common.remote.domain;

public class VerifyCardDomain {
	private String real_name="";// 户名 1..1 s..4 真实姓名 必填
	private String cmer="";// 商户名称 1..1 S..6-20 必填(地域+品牌+经营属性)
	private String cmer_sort="";// 商户简称 1.1 S.6-13 必填
	private String phone="";// 联系电话 1..1 s..50 联系电话 必填
	private String business_id="";// 商户经营类别 1.1 s.10
								// 所属MCC(经营类目表)的ID(只需传对应的微信类目ID)必填
	private String channel_code="";// 支付通道 1.1 s.50 WXPAY：微信 ALIPAY：支付宝 UNIPAY:银联
								// 必填
	private String card_type="";// 卡类型 1..1 s..1 参考demo默认值 必填
	private String card_no="";// 卡号 1..1 s..30 只允许个人卡号 必填
	private String cert_type="";// 证件类型 1..1 s..1 参考demo默认值 必填
	private String cert_no="";// 证件号 1..1 s..30 必填
	private String mobile="";// 开户时绑定手机号 1..1 s..11 必填
	private String location="";// 开户城市 1..1 s..10 必填
	private String cert_correct="";// 身份证正面图片 1..1 s..20 图片地址 非必填
	private String cert_opposite="";// 身份证背面图片 1..1 s..20 图片地址 非必填
	private String cert_meet="";// 手持身份证图片 1..1 s..20 图片地址 非必填
	private String card_correct="";// 银行卡正面图片 1..1 s..20 图片地址 非必填
	private String card_opposite="";// 银行卡背面图片 1..1 s..20 图片地址 非必填

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getCmer() {
		return cmer;
	}

	public void setCmer(String cmer) {
		this.cmer = cmer;
	}

	public String getCmer_sort() {
		return cmer_sort;
	}

	public void setCmer_sort(String cmer_sort) {
		this.cmer_sort = cmer_sort;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCert_correct() {
		return cert_correct;
	}

	public void setCert_correct(String cert_correct) {
		this.cert_correct = cert_correct;
	}

	public String getCert_opposite() {
		return cert_opposite;
	}

	public void setCert_opposite(String cert_opposite) {
		this.cert_opposite = cert_opposite;
	}

	public String getCert_meet() {
		return cert_meet;
	}

	public void setCert_meet(String cert_meet) {
		this.cert_meet = cert_meet;
	}

	public String getCard_correct() {
		return card_correct;
	}

	public void setCard_correct(String card_correct) {
		this.card_correct = card_correct;
	}

	public String getCard_opposite() {
		return card_opposite;
	}

	public void setCard_opposite(String card_opposite) {
		this.card_opposite = card_opposite;
	}

}
