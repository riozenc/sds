/**
 * Title:BalanceMerchantLogDomain.java
 * Author:riozenc
 * Datetime:2017年3月6日 下午5:24:54
**/
package sds.webapp.blc.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

public class BalanceMerchantLogDomain extends Page<BalanceMerchantLogDomain> implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;// id int 11 0 0 0 0 0 0 0 -1 0
	private Integer targetId;// target_id int 11 0 -1 0 0 0 0 0 0 0
	private String account;// account varchar 255 0 -1 0 0 0 0 0 utf8
							// utf8_general_ci 0 0
	private String orderId;// 订单号
	private Date orderDate;// 订单时间
	private BigDecimal balance;// balance decimal 20 3 -1 0 0 0 0 0 0 0
	private Date createDate;// create_date datetime 0 0 -1 0 0 0 0 0 0 0
	private Date updateDate;// update_date datetime 0 0 -1 0 0 0 0 0 0 0
	private String remark;// remark varchar 255 0 -1 0 0 0 0 0 utf8
							// utf8_general_ci 0 0
	private Integer status;// 0失效，1转入，2转出，3重算
	private String operation;// operation varchar 255 0 -1 0 0 0 0 0 操作解释：转入、转出
								// utf8 utf8_general_ci 0 0
	private String realName; //商户名称
	private String tjName;//推荐人增加 2017.4.3
	private String tjAccount;//推荐人手机号增加2017.4.3
	private BigDecimal amount; //增加显示金额功能 2017.4.3
	
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTjAccount() {
		return tjAccount;
	}

	public void setTjAccount(String tjAccount) {
		this.tjAccount = tjAccount;
	}

	public String getTjName() {
		return tjName;
	}

	public void setTjName(String tjName) {
		this.tjName = tjName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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
