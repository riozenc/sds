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

}
