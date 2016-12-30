package sds.webapp.stm.domain;

import java.util.Date;

import sds.webapp.acc.domain.UserDomain;

/**
 * 结算
 * 
 * @author riozenc
 *
 */
public class StmDomain {
	private String orderId;// 订单号
	private String merchantAccount;// 商户
	private Double amount;// 消费金额
	private Integer paymentChannle;// 支付通道
	private Double merchantRate;// 商户费率
	private Double rateDiff;// 费率差
	private UserDomain agent;// 代理商
	private boolean isMain;// 是否为总代理商
	private UserDomain parentAgent;// 上级代理商ID
	private Date orderDate;// 交易日期
	private Date jsDate;// 结算日期

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getPaymentChannle() {
		return paymentChannle;
	}

	public void setPaymentChannle(Integer paymentChannle) {
		this.paymentChannle = paymentChannle;
	}

	public Double getMerchantRate() {
		return merchantRate;
	}

	public void setMerchantRate(Double merchantRate) {
		this.merchantRate = merchantRate;
	}

	public UserDomain getAgent() {
		return agent;
	}

	public void setAgent(UserDomain agent) {
		this.agent = agent;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

	public UserDomain getParentAgent() {
		return parentAgent;
	}

	public void setParentAgent(UserDomain parentAgent) {
		this.parentAgent = parentAgent;
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

	public Double getRateDiff() {
		return rateDiff;
	}

	public void setRateDiff(Double rateDiff) {
		this.rateDiff = rateDiff;
	}

}
