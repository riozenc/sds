package sds.common.security.realm;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.riozenc.quicktool.common.util.cryption.en.WebPasswordUtils;

import sds.common.exception.InvalidAccountException;
import sds.common.security.Principal;
import sds.common.security.token.UsernamePasswordToken;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.acc.service.UserService;

public class PasswordShiroRealm extends AuthorizingRealm {
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;
	// userServiceImpl,RIOZENC_userServiceImpl

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// TODO Auto-generated method stub

		// 登录成功授权操作
		Principal principal = (Principal) getAvailablePrincipal(principalCollection);

		if (!principal.isMobileLogin()) {
			// web登录
			if (principal.getId() == 1) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

				info.addRole("updateRate");// 修改费率
				info.addRole("withdrawalsCheck");// 提现申请

				return info;
			}
		}

		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// TODO Auto-generated method stub

		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

		String username = token.getUsername();
		if (username != null && !"".equals(username.trim())) {
			if (token.isMobileLogin()) {// 手机登录
				// 商户

				MerchantDomain merchantDomain = merchantService.getUser(new MerchantDomain(token));
				if (merchantDomain != null) {
					String password = WebPasswordUtils.encodePassword(merchantDomain.getPassword());
					try {

						byte[] salt = Hex.decodeHex(password.substring(0, 16).toCharArray());
						return new SimpleAuthenticationInfo(new Principal(merchantDomain), password.substring(16),
								ByteSource.Util.bytes(salt), getName());
					} catch (DecoderException e) {
						// TODO Auto-generated catch block
						throw new AuthenticationException("密码错误...");
					}
				} else {
					throw new InvalidAccountException("msg:账户不存在...");
				}

			} else {
				// 代理商
				UserDomain userDomain = userService.getUser(new UserDomain(token));
				if (userDomain != null) {
					String password = userDomain.getPassword().substring(16);
					try {
						byte[] salt = Hex.decodeHex(userDomain.getPassword().substring(0, 16).toCharArray());
						return new SimpleAuthenticationInfo(new Principal(userDomain), password,
								ByteSource.Util.bytes(salt), getName());
					} catch (DecoderException e) {
						// TODO Auto-generated catch block
						throw new AuthenticationException("密码错误...");
					}
				}
			}

		}

		return null;
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-512");
		matcher.setHashIterations(1024);// 迭代1024次
		setCredentialsMatcher(matcher);
	}
}
