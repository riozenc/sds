/**
 * Title:UserRoleDomain.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午2:52:10
**/
package sds.webapp.ump.domain;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;

public class UserRoleDomain implements MybatisEntity{
	@TablePrimaryKey
	private Integer id;//
	private Integer userId;//
	private Integer roleId;//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
