package sds.webapp.stm.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;

/**
 * 利润
 * 
 * @author riozenc
 *
 */
public class ProfitDomain implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;// `id` int(11) NOT NULL AUTO_INCREMENT,
	private String orderId;// '订单号',
	private String account;// `account` varchar(255) DEFAULT NULL COMMENT '商户号',
	private Double amount;// `amount` decimal(20,2) DEFAULT NULL COMMENT '消费金额',
	private Double merchantProfit;// 商户利润
	private Double totalProfit;// '分润总额',
	private Integer agentId;// 代理商ID
	private Double agentProfit;// 代理商利润
	private boolean isReferee;// 推荐人功能是否开启
	private Double tjProfit;// '推荐人分润金额',
	private Integer tjId;// 推荐人ID
	private Date orderDate;// '交易日期',
	private Date jsDate;// '结算时间',
	private Date createDate;// 创建日期
	private Integer status;// 状态：0失效，1有效，2统计完毕
	private String merchantName;// 商户名称
	private String agentName;// 代理商全称
	private String agentAccount;// 代理商电话
	private String fullName; //公司名称 2017.3.29修改
	private String tjName;//推荐人名字 2017.3.29修改
	private String cmer;//下级分润中人名 2017.3.30修改
	private Double wxRate;//微信微信手续费
	private Double aliRate;//支付宝手续费
	private Double unipayRate;//银联手续费
	

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

	public Double getUnipayRate() {
		return unipayRate;
	}

	public void setUnipayRate(Double unipayRate) {
		this.unipayRate = unipayRate;
	}

	public String getCmer() {
		return cmer;
	}

	public void setCmer(String cmer) {
		this.cmer = cmer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Double getAgentProfit() {
		return agentProfit;
	}

	public void setAgentProfit(Double agentProfit) {
		this.agentProfit = agentProfit;
	}

	public Double getTjProfit() {
		return tjProfit;
	}

	public void setTjProfit(Double tjProfit) {
		this.tjProfit = tjProfit;
	}

	public Integer getTjId() {
		return tjId;
	}

	public void setTjId(Integer tjId) {
		this.tjId = tjId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getJsDate() {
		return jsDate;
	}

	public void setJsDate(Date jsDate) {
		this.jsDate = jsDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isReferee() {
		return isReferee;
	}

	public void setReferee(boolean isReferee) {
		this.isReferee = isReferee;
	}

	public Double getMerchantProfit() {
		return merchantProfit;
	}

	public void setMerchantProfit(Double merchantProfit) {
		this.merchantProfit = merchantProfit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	public String getAgentAccount() {
		return agentAccount;
	}

	public void setAgentAccount(String agentAccount) {
		this.agentAccount = agentAccount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTjName() {
		return tjName;
	}

	public void setTjName(String tjName) {
		this.tjName = tjName;
	}


}
