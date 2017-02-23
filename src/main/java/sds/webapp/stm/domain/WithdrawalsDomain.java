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

}
