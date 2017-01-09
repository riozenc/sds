/**
 * 	Title:sds.webapp.acc.domain
 *		Datetime:2016年12月16日 上午9:38:37
 *		Author:czy
 */
package sds.webapp.acc.domain;

import java.util.Date;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

public class UserDomain extends Page implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;
	private Integer parentId;// `parent_id` int(255) DEFAULT NULL COMMENT
								// '所属上级代理商',
	private String account;// `account` varchar(20) DEFAULT NULL COMMENT
							// '登录手机号',
	private String password;// `password` varchar(50) DEFAULT NULL COMMENT
							// '登录密码',
	private String fullName;// `full_name` varchar(255) DEFAULT NULL COMMENT
							// '企业全称',
	private String simpleName;// private`abb_name` varchar(255) DEFAULT NULL
								// COMMENT '企业简称',
	private String agType;// `ag_type` varchar(255) DEFAULT NULL COMMENT '企业类型',
	private String agAddress;// `ag_address` varchar(255) DEFAULT NULL COMMENT
								// '公司地址',
	private String regMoney;// `reg_money` varchar(255) DEFAULT NULL COMMENT
							// '注册资本',
	private String regName;// `reg_name` varchar(255) DEFAULT NULL COMMENT
							// '法人姓名',
	private String regAddress;// `reg_address` varchar(255) DEFAULT NULL COMMENT
								// '身份证地址',
	private Date regDate;// `reg_date` varchar(255) DEFAULT NULL COMMENT '成立日期',
	private String regExt;// `reg_ext` varchar(255) DEFAULT NULL COMMENT '经营范围',
	private String regNo;// `reg_no` varchar(255) DEFAULT NULL COMMENT '营业执照编号',
	private String busTerm;// `bus_term` varchar(255) DEFAULT NULL COMMENT
							// '营业期限',
	private String busAno;// `bus_ano` varchar(255) DEFAULT NULL COMMENT
							// '机构组织代码',
	private String busSno;// `bus_sno` varchar(255) DEFAULT NULL COMMENT
							// '税务登记号',
	private Double costWrate;// `cost_wrate` varchar(255) DEFAULT NULL COMMENT
								// '代理商微信成本费率',
	private Double costKrate;// `cost_krate` varchar(255) DEFAULT NULL COMMENT
								// '代理商快捷成本费率',
	private Double costArate;// `cost_arate` varchar(255) DEFAULT NULL COMMENT
								// '代理商支付宝成本费率',
	private Double userWrate;// `user_wrate` varchar(255) DEFAULT NULL COMMENT
								// '商户微信默认费率',
	private Double userArate;// `user_arate` varchar(255) DEFAULT NULL COMMENT
								// '商户支付宝默认费率',
	private Double userKrate;// `user_krate` varchar(255) DEFAULT NULL COMMENT
								// '商户快捷默认费率',
	private String jsActype;// `js_actype` varchar(255) DEFAULT NULL COMMENT
							// '结算人账户类型',
	private String jsCard;// `js_card` varchar(255) DEFAULT NULL COMMENT
							// '结算人开户账号',
	private String jsAddress;// `js_address` varchar(255) DEFAULT NULL COMMENT
								// '开户地',
	private String jsBank;// `js_bank` varchar(255) DEFAULT NULL COMMENT '开户银行',
	private String jsBankadd;// `js_bankadd` varchar(255) DEFAULT NULL COMMENT
								// '开户行所属支行',
	private String jsName;// `js_name` varchar(255) DEFAULT NULL COMMENT '开户名',
	private String jsLhno;// `js_lhno` varchar(255) DEFAULT NULL COMMENT '联行号',
	private String createId;// `create_id` varchar(255) DEFAULT NULL COMMENT
							// '创建者账号',
	private Date createDate;// `create_date` varchar(255) DEFAULT NULL COMMENT
							// '创建时间',

	private String regCard;// 开户人身份证
	private Integer tjStatus;
	private Double tjRate;
	private Double tjLimit;

	private Integer status;

	public UserDomain() {
	}

	public UserDomain(UsernamePasswordToken usernamePasswordToken) {
		this.account = usernamePasswordToken.getUsername();// 手机号
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getAgType() {
		return agType;
	}

	public void setAgType(String agType) {
		this.agType = agType;
	}

	public String getAgAddress() {
		return agAddress;
	}

	public void setAgAddress(String agAddress) {
		this.agAddress = agAddress;
	}

	public String getRegMoney() {
		return regMoney;
	}

	public void setRegMoney(String regMoney) {
		this.regMoney = regMoney;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegExt() {
		return regExt;
	}

	public void setRegExt(String regExt) {
		this.regExt = regExt;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getBusTerm() {
		return busTerm;
	}

	public void setBusTerm(String busTerm) {
		this.busTerm = busTerm;
	}

	public String getBusAno() {
		return busAno;
	}

	public void setBusAno(String busAno) {
		this.busAno = busAno;
	}

	public String getBusSno() {
		return busSno;
	}

	public void setBusSno(String busSno) {
		this.busSno = busSno;
	}

	public Double getCostWrate() {
		return costWrate;
	}

	public void setCostWrate(Double costWrate) {
		this.costWrate = costWrate;
	}

	public Double getCostKrate() {
		return costKrate;
	}

	public void setCostKrate(Double costKrate) {
		this.costKrate = costKrate;
	}

	public Double getCostArate() {
		return costArate;
	}

	public void setCostArate(Double costArate) {
		this.costArate = costArate;
	}

	public Double getUserWrate() {
		return userWrate;
	}

	public void setUserWrate(Double userWrate) {
		this.userWrate = userWrate;
	}

	public Double getUserArate() {
		return userArate;
	}

	public void setUserArate(Double userArate) {
		this.userArate = userArate;
	}

	public Double getUserKrate() {
		return userKrate;
	}

	public void setUserKrate(Double userKrate) {
		this.userKrate = userKrate;
	}

	public String getJsActype() {
		return jsActype;
	}

	public void setJsActype(String jsActype) {
		this.jsActype = jsActype;
	}

	public String getJsCard() {
		return jsCard;
	}

	public void setJsCard(String jsCard) {
		this.jsCard = jsCard;
	}

	public String getJsAddress() {
		return jsAddress;
	}

	public void setJsAddress(String jsAddress) {
		this.jsAddress = jsAddress;
	}

	public String getJsBank() {
		return jsBank;
	}

	public void setJsBank(String jsBank) {
		this.jsBank = jsBank;
	}

	public String getJsBankadd() {
		return jsBankadd;
	}

	public void setJsBankadd(String jsBankadd) {
		this.jsBankadd = jsBankadd;
	}

	public String getJsName() {
		return jsName;
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}

	public String getJsLhno() {
		return jsLhno;
	}

	public void setJsLhno(String jsLhno) {
		this.jsLhno = jsLhno;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRegCard() {
		return regCard;
	}

	public void setRegCard(String regCard) {
		this.regCard = regCard;
	}

	public Integer getTjStatus() {
		return tjStatus;
	}

	public void setTjStatus(Integer tjStatus) {
		this.tjStatus = tjStatus;
	}

	public Double getTjRate() {
		return tjRate;
	}

	public void setTjRate(Double tjRate) {
		this.tjRate = tjRate;
	}

	public Double getTjLimit() {
		return tjLimit;
	}

	public void setTjLimit(Double tjLimit) {
		this.tjLimit = tjLimit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
