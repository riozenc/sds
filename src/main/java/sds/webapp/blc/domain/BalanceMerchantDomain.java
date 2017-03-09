/**
 * Title:BalanceMerchantDomain.java
 * Author:riozenc
 * Datetime:2017年3月6日 下午4:32:54
**/
package sds.webapp.blc.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;

public class BalanceMerchantDomain implements MybatisEntity {

	@TablePrimaryKey
	private Integer id;// `id` int(11) NOT NULL,
	private Integer targetId;// `target_id` int(11) DEFAULT NULL,
	private String account;// `account` varchar(255) DEFAULT NULL,
	private BigDecimal balance;// `balance` decimal(20,3) DEFAULT NULL,
	private BigDecimal countIn;// 总收入
	private BigDecimal countOut;// 总支出
	private Date createDate;// `create_date` datetime DEFAULT NULL,
	private Date updateDate;// `update_date` datetime DEFAULT NULL,
	private String remark;// `remark` varchar(255) DEFAULT NULL,
	private Integer status;// `status` int(1) DEFAULT NULL,

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

	public BigDecimal getCountIn() {
		return countIn;
	}

	public void setCountIn(BigDecimal countIn) {
		this.countIn = countIn;
	}

	public BigDecimal getCountOut() {
		return countOut;
	}

	public void setCountOut(BigDecimal countOut) {
		this.countOut = countOut;
	}

}
