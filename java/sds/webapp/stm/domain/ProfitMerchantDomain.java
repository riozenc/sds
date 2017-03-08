package sds.webapp.stm.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

public class ProfitMerchantDomain extends Page<ProfitMerchantDomain> implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;// `id` int(11) NOT NULL AUTO_INCREMENT,
	private Integer merchantId;// `merchant_id` int(11) DEFAULT NULL COMMENT
								// '商户ID',
	private Double totalAmount;// `total_amount` decimal(20,2) DEFAULT '0.00'
								// COMMENT '交易总金额',
	private Double totalProfit;// `total_profit` decimal(20,2) DEFAULT NULL
								// COMMENT '所获分润总金额',
	private Date date;// 分润时间

	private Integer status;// `status` tinyint(4) DEFAULT NULL COMMENT
							// '状态0未提现1提现',

	private Date startDate;// 分润时间区间
	private Date endDate;// 分润时间区间

	private String account;// 手机号

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

}
