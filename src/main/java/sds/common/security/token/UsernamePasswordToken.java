package sds.common.security.token;

public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6878799966828978549L;
	private String captcha;
	private boolean mobileLogin;

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha,
			boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}
}
