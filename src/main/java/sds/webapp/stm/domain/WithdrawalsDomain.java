/**
 * Title:WithdrawalsDomain.java
 * Author:riozenc
 * Datetime:2017年2月21日 上午11:20:52
**/
package sds.webapp.stm.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

/**
 * 提现
 * 
 * @author riozenc
 *
 */
public class WithdrawalsDomain extends Page<WithdrawalsDomain> implements MybatisEntity {

	@TablePrimaryKey
	private Integer id;
	private Integer merchantId;
	private Double amount;
	private Date date;
	private String operator;
	private Integer status;

	private Date startDate;
	private Date endDate;
	private String account;
	private Double balance;
	private String realName;//2017.4.10增加显示提现明细人

	


	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

}
