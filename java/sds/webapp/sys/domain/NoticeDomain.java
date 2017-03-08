/**
 * Title:NoticeDomain.java
 * Author:riozenc
 * Datetime:2017年2月26日 上午10:31:01
**/
package sds.webapp.sys.domain;

import java.util.Date;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;
import com.riozenc.quicktool.mybatis.persistence.Page;

import sds.webapp.ord.domain.OrderDomain;

/**
 * 通知通告
 * 
 * @author riozenc
 *
 */
public class NoticeDomain extends Page<OrderDomain> implements MybatisEntity {
	@TablePrimaryKey
	private Integer id;// `ID` int(11) NOT NULL,
	private String title;// 消息标题
	private String message;// `MESSAGE` varchar(255) DEFAULT NULL,
	private Date date;// `DATE` datetime DEFAULT NULL,
	private String operator;// `OPERATOR` varchar(255) DEFAULT NULL,
	private String remark;// `REMARK` varchar(255) DEFAULT NULL,
	private Integer status;// `STATUS` int(1) DEFAULT NULL,

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
