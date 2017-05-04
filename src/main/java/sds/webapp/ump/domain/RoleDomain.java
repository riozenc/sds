/**
 * Title:RoleDomain.java
 * Author:riozenc
 * Datetime:2017年4月26日 下午2:47:29
**/
package sds.webapp.ump.domain;

import com.riozenc.quicktool.annotation.TablePrimaryKey;
import com.riozenc.quicktool.mybatis.MybatisEntity;

public class RoleDomain implements MybatisEntity{
	@TablePrimaryKey
	private Integer id;//
	private String roleName;//
	
	private Integer userId;
	private Integer roleId;
	private Integer rolesId;
	private Integer isCheck; //标识有没有打勾   0表示没勾 1表示勾中
	
	
	
	
	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
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

	public Integer getRolesId() {
		return rolesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

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

	@Override  
	public boolean equals(Object obj) {  
	RoleDomain s=(RoleDomain)obj;   
	return roleName.equals(s.roleName);   
	}

	@Override
	public int hashCode() {
		Integer in = id;  
		return in.hashCode();
	}  

}
