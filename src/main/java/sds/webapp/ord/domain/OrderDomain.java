package sds.webapp.ord.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;

public class OrderDomain implements MybatisEntity{
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
	private String codeUrl;// `code_url` varchar(255) DEFAULT NULL COMMENT
							// '二维码支付地址',
	private String remark;// `remark` varchar(255) DEFAULT NULL COMMENT '备注',
	private String returnCode;// `return_code` varchar(255) DEFAULT NULL COMMENT
								// '查询返回码',
	private Integer status;// `status` int(4) NOT NULL DEFAULT '0' COMMENT
							// '0未查询1成功2失败',

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

}
