/**
 * Title:InvalidAccountException.java
 * Author:riozenc
 * Datetime:2017年3月30日 下午4:43:49
**/
package sds.common.exception;

import org.apache.shiro.authc.AuthenticationException;

import com.riozenc.quicktool.common.util.log.ExceptionLogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

/**
 * 不存在的账户
 * 
 * @author riozenc
 *
 */
public class InvalidAccountException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5952144638817513908L;

	public InvalidAccountException() {
		super();
	}

	public InvalidAccountException(String message) {
		super(message);
		LogUtil.getLogger(LOG_TYPE.OTHER).error(message + ExceptionLogUtil.log(this));
	}

	public InvalidAccountException(Throwable cause) {
		super(cause);
	}

	public InvalidAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
