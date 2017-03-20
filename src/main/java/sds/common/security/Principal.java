/**
 * 	Title:sds.common.security
 *		Datetime:2016年12月16日 上午9:36:50
 *		Author:czy
 */
package sds.common.security;

import java.util.Date;

import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;

public class Principal {
	private Integer id;//
	private String userId; // 登录名
	private String userName; // 姓名
	private String phone;//
	private String mailAddress;
	private Integer sex;// SEX 性别 int FALSE FALSE FALSE
	private String imageUrl;// IMAGE_URL 头像 varchar(20) 20 FALSE FALSE FALSE
	private boolean mobileLogin; // 是否手机登录
	private Date createDate;// 创建时间 CREATE_DATE datetime
	private Date lastLoginDate;// 最后登陆时间 LAST_LOGIN_DATE datetime
	private Date updateDate;// 最后更新时间 UPDATE_DATE datetime

	private UserDomain userDomain;
	private MerchantDomain merchantDomain;

	public Principal() {

	}

	public Principal(MerchantDomain merchantDomain) {
		merchantDomain.setPassword(null);
		this.merchantDomain = merchantDomain;
		this.id = merchantDomain.getId();
		this.userId = merchantDomain.getAccount();
		this.userName = merchantDomain.getRealName();
	}

	public Principal(UserDomain userDomain) {
		userDomain.setPassword(null);
		this.userDomain = userDomain;
		this.id = userDomain.getId();
		this.userId = userDomain.getAccount();
		this.userName = userDomain.getFullName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public void setMobileLogin(boolean mobileLogin) {
		this.mobileLogin = mobileLogin;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public UserDomain getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}

	public MerchantDomain getMerchantDomain() {
		return merchantDomain;
	}

	public void setMerchantDomain(MerchantDomain merchantDomain) {
		this.merchantDomain = merchantDomain;
	}

}
