package sds.webapp.stm.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

public class ProfitUserDomain extends Page<ProfitUserDomain> implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;// `id` int(11) NOT NULL AUTO_INCREMENT,
	private Integer agentId;// `agent_id` int(11) DEFAULT NULL COMMENT '代理商id',
	private Double totalAmount;// `total_amount` decimal(20,2) DEFAULT '0.00'
								// COMMENT '交易总金额',
	private Double totalProfit;// `total_profit` decimal(20,2) DEFAULT NULL
								// COMMENT '所获分润总金额',
	private Date date;// 分润时间
	private Integer status;// `status` tinyint(4) DEFAULT NULL COMMENT
							// '状态0未提现1提现',
	private Date startDate;// 分润时间区间
	private Date endDate;// 分润时间区间
	private String fullName; // 公司名称 2017.3.29 修改
	private String account;// 手机号
	// excel用
	private String regName;// 法人姓名
	private String jsCard;//
	private String jsAddress;//
	private String jsBank;
	private String jsBankadd;
	private String jsName;
	private String jsLhno;
	private String agentName;// 代理商全称
	private String cmer;//下级分润中的人名

	public String getCmer() {
		return cmer;
	}

	public void setCmer(String cmer) {
		this.cmer = cmer;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
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

}
