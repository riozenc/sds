package sds.webapp.acc.domain;

import java.util.Date;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

import sds.webapp.stm.domain.StmElement;

public class MerchantDomain extends Page<MerchantDomain> implements MybatisEntity, StmElement {
	@TablePrimaryKey
	private Integer id;// `id` int(11) NOT NULL AUTO_INCREMENT,
	private String account;// `account` varchar(255) DEFAULT NULL COMMENT
							// '登录手机号,唯一的',
	private String password;// `pass` varchar(255) DEFAULT NULL COMMENT '密 码',
	private String privatekey;// `privatekey` longtext COMMENT '私钥',
	private Date createDate;// `create_date` datetime NOT NULL DEFAULT
							// CURRENT_TIMESTAMP COMMENT '创建时间',
	private String realName;// `real_name` varchar(255) DEFAULT NULL COMMENT
							// '真实姓名',
	private String cmer;// `cmer` varchar(255) DEFAULT NULL COMMENT '商户名称',
	private String cmerSort;// `cmer_sort` varchar(255) DEFAULT NULL COMMENT
							// '商户简称',
	private Integer channelCode;// `channel_code` varchar(255) DEFAULT NULL
								// COMMENT '支付通道，默认WXPAY',
	private Integer level;// 等级
	private String businessId;// `business_id` varchar(255) DEFAULT NULL COMMENT
								// '行业代码',
	private String cardNo;// `card_no` varchar(255) DEFAULT NULL COMMENT '卡号',
	private String certNo;// `cert_no` varchar(255) DEFAULT NULL COMMENT '证件号',
	private String phone;// `phone` varchar(255) DEFAULT NULL COMMENT '电话',
	private String mobile;// `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
	private String location;// `location` varchar(255) DEFAULT NULL COMMENT
							// '开户城市',
	private String certCorrect;// `cert_correct` varchar(255) DEFAULT NULL
								// COMMENT '身份证正面图片',
	private String certOpposite;// `cert_opposite` varchar(255) DEFAULT NULL
								// COMMENT '身份证背面图片',
	private String certMeet;// `cert_meet` varchar(255) DEFAULT NULL COMMENT
							// '手持身份证图片',
	private String cardCorrect;// `card_correct` varchar(255) DEFAULT NULL
								// COMMENT '银行卡正面图片',
	private String cardOpposite;// `card_opposite` varchar(255) DEFAULT NULL
								// COMMENT '银行卡背面图片',
	private String businessPic;// `business_pic` varchar(255) DEFAULT NULL
								// COMMENT '营业照',
	private String doorPic;// `door_pic` varchar(255) DEFAULT NULL COMMENT
							// '门头照',

	private Integer status;// `status` tinyint(4) DEFAULT '0' COMMENT
							// '0审核中，1审核成功，2注销用户',
	private String other;// `other` varchar(255) DEFAULT NULL COMMENT '失败原因',
	private Double wxRate;// `wx_rate` double(10,5) NOT NULL DEFAULT '0' COMMENT
							// '微信费率',
	private Double aliRate;// `ali_rate` double(10,5) DEFAULT NULL COMMENT
							// '支付宝费率',
	private Double unipayRate;// 银联费率
	private String appCode;// `appcode` varchar(255) DEFAULT NULL COMMENT '推广码',

	private Integer userType;// `user_type` tinyint(4) DEFAULT '1' COMMENT
								// '用户类型，1普通用户，2企业用户',
	private String busPic;// `bus_pic` varchar(255) DEFAULT NULL COMMENT
							// '营业执照照片',
	private String busNo;// `bus_no` varchar(255) DEFAULT NULL COMMENT
							// '营业执照注册号',

	private String bank;// 银行卡所属银行
	private String codePayUrl;// 柜台码支付地址
	private String cbzid;// 预留
	private String code;// 预留

	private Integer tjId;// 推荐人ID
	private Integer agentId;// `agent_id` int(11) DEFAULT NULL COMMENT '所属代理商',
	private String parentName;// 上级名称

	@JsonIgnore
	private String virtualAccount;
	@JsonIgnore
	private MerchantDomain tj;// 对应的推荐人

	public MerchantDomain() {
	}

	public MerchantDomain(UsernamePasswordToken usernamePasswordToken) {
		this.account = usernamePasswordToken.getUsername();// 手机号
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCmer() {
		return cmer;
	}

	public void setCmer(String cmer) {
		this.cmer = cmer;
	}

	public String getCmerSort() {
		return cmerSort;
	}

	public void setCmerSort(String cmerSort) {
		this.cmerSort = cmerSort;
	}

	public Integer getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(Integer channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getCertCorrect() {
		return certCorrect;
	}

	public void setCertCorrect(String certCorrect) {
		this.certCorrect = certCorrect;
	}

	public String getCertOpposite() {
		return certOpposite;
	}

	public void setCertOpposite(String certOpposite) {
		this.certOpposite = certOpposite;
	}

	public String getCertMeet() {
		return certMeet;
	}

	public void setCertMeet(String certMeet) {
		this.certMeet = certMeet;
	}

	public String getCardCorrect() {
		return cardCorrect;
	}

	public void setCardCorrect(String cardCorrect) {
		this.cardCorrect = cardCorrect;
	}

	public String getCardOpposite() {
		return cardOpposite;
	}

	public void setCardOpposite(String cardOpposite) {
		this.cardOpposite = cardOpposite;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Double getWxRate() {
		return wxRate;
	}

	public void setWxRate(Double wxRate) {
		this.wxRate = wxRate;
	}

	public Double getAliRate() {
		return aliRate;
	}

	public void setAliRate(Double aliRate) {
		this.aliRate = aliRate;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Integer getTjId() {
		return tjId;
	}

	public void setTjId(Integer tjId) {
		this.tjId = tjId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getBusPic() {
		return busPic;
	}

	public void setBusPic(String busPic) {
		this.busPic = busPic;
	}

	public String getBusNo() {
		return busNo;
	}

	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getVirtualAccount() {
		return virtualAccount;
	}

	public void setVirtualAccount(String virtualAccount) {
		this.virtualAccount = virtualAccount;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getBusinessPic() {
		return businessPic;
	}

	public void setBusinessPic(String businessPic) {
		this.businessPic = businessPic;
	}

	public String getDoorPic() {
		return doorPic;
	}

	public void setDoorPic(String doorPic) {
		this.doorPic = doorPic;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCodePayUrl() {
		return codePayUrl;
	}

	public void setCodePayUrl(String codePayUrl) {
		this.codePayUrl = codePayUrl;
	}

	public String getCbzid() {
		return cbzid;
	}

	public void setCbzid(String cbzid) {
		this.cbzid = cbzid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getUnipayRate() {
		return unipayRate;
	}

	public void setUnipayRate(Double unipayRate) {
		this.unipayRate = unipayRate;
	}

	@Override
	public Double getRate(int channelCode) {
		// TODO Auto-generated method stub
		Double rate = null;
		switch (channelCode) {
		case 1:// 微信
			rate = getWxRate();
			break;
		case 2:// 支付宝
			rate = getWxRate();
			break;
		case 3:// 快捷
			rate = getUnipayRate();
			break;
		default:
			rate = getWxRate();
			break;
		}
		if (rate == null || rate == 0) {
			throw new RuntimeException(getAccount() + "费率为空,无法计算分润..");
		}
		return rate;
	}

}
