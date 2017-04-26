/**
 * Title:RoleDomain.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午2:47:29
**/
package sds.webapp.ump.domain;

import com.riozenc.quicktool.annotation.TablePrimaryKey;

public class RoleDomain {
	@TablePrimaryKey
	private Integer id;//
	private String roleName;//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
