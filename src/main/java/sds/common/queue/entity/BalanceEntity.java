/**
 * Title:BalanceEntity.java
 * Author:riozenc
 * Datetime:2017年3月6日 上午10:52:14
**/
package sds.common.queue.entity;

import java.math.BigDecimal;
import java.util.Date;

import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.WithdrawalsDomain;

/**
 * 余额计算模型
 * 
 * @author riozenc
 *
 */
public class BalanceEntity {

	private Integer id;//
	private Integer targetId;//
	private String account;//
	private BigDecimal amount;// 变动金额
	private String orderId;// 对应订单号
	private Date orderDate;// 订单时间
	private int type;// 操作：1转入，2转出，3重算

	public BalanceEntity(ProfitDomain profitDomain) {
		// this.account = profitDomain.getAccount();//分润的电话是商户电话
		this.targetId = profitDomain.getTjId();
		this.amount = BigDecimal.valueOf(profitDomain.getTjProfit());
		this.orderId = profitDomain.getOrderId();
		this.orderDate = profitDomain.getOrderDate();
		this.type = 1;
	}

	public BalanceEntity(WithdrawalsDomain withdrawalsDomain) {
		this.account = withdrawalsDomain.getAccount();
		this.targetId = withdrawalsDomain.getMerchantId();
		this.amount = BigDecimal.valueOf(withdrawalsDomain.getAmount());
		this.type = 2;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public BalanceEntity setType(int type) {
		this.type = type;
		return this;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
