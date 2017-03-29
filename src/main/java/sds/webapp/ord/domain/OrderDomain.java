package sds.webapp.ord.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

public class OrderDomain extends Page<OrderDomain> implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;// `id` int(11) NOT NULL AUTO_INCREMENT,
	private String orderId;// `order_id` varchar(255) DEFAULT NULL COMMENT
							// '订单号',
	private String orderNo;// `order_no` varchar(255) DEFAULT NULL COMMENT
							// '微信订单号',
	private Integer channelCode;// `channel_code` varchar(255) DEFAULT NULL
								// COMMENT '支付通道',
	private String respCode;// `resp_code` varchar(255) DEFAULT NULL COMMENT
							// '交易返回码',
	private String respInfo;// `resp_info` varchar(200) DEFAULT NULL COMMENT
							// '返回码描述',
	private Double amount;// `amount` double(10,5) DEFAULT NULL COMMENT '交易金额',

	private Date date;// `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
						// COMMENT '交易日期',
	private String account;// `account` varchar(255) DEFAULT NULL COMMENT
							// '商户账号',
	private String cmer; //公司名称  2017.3.28修改
	private String proxyAccount;// 代理商户账号
	private String codeUrl;// `code_url` varchar(255) DEFAULT NULL COMMENT
							// '二维码支付地址',
	private String remark;// `remark` varchar(255) DEFAULT NULL COMMENT '备注',
	private String returnCode;// `return_code` varchar(255) DEFAULT NULL COMMENT
								// '查询返回码',
	private Integer status;// `status` int(4) NOT NULL DEFAULT '0' COMMENT
							// '0未查询1成功2失败'

	private double minamount;// 最小金额
	private double amountfee;// 手续费
	private double fee;// 费率
	private Date startDate;// 起始时间
	private Date endDate;// 截至时间
	private String merchantName;//
	private String realName;//
	
	public String getCmer(){
		return cmer;
	}
	public void setCmer(String cmer){
		this.cmer=cmer;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(Integer channelCode) {
		this.channelCode = channelCode;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProxyAccount() {
		return proxyAccount;
	}

	public void setProxyAccount(String proxyAccount) {
		this.proxyAccount = proxyAccount;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public double getMinamount() {
		return minamount;
	}

	public void setMinamount(double minamount) {
		this.minamount = minamount;
	}

	public double getAmountfee() {
		return amountfee;
	}

	public void setAmountfee(double amountfee) {
		this.amountfee = amountfee;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

}
