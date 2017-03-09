/**
 * Title:BalanceEntity.java
 * Author:riozenc
 * Datetime:2017年3月6日 上午10:52:14
**/
package sds.common.queue.entity;

import java.math.BigDecimal;

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
	private int type;// 操作：1转入，2转出

	public BalanceEntity(ProfitDomain profitDomain) {
//		this.account = profitDomain.getAccount();//分润的电话是商户电话
		this.targetId = profitDomain.getTjId();
		this.amount = new BigDecimal(profitDomain.getTjProfit());
		this.type = 1;
	}

	public BalanceEntity(WithdrawalsDomain withdrawalsDomain) {
		this.account = withdrawalsDomain.getAccount();
		this.targetId = withdrawalsDomain.getMerchantId();
		this.amount = new BigDecimal("-"+withdrawalsDomain.getAmount());
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

	public void setType(int type) {
		this.type = type;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

}
