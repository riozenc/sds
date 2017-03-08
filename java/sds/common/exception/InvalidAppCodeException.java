package sds.common.exception;

import com.riozenc.quicktool.common.util.log.ExceptionLogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

/**
 * 无效的邀请码
 * 
 * @author riozenc
 *
 */
public class InvalidAppCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -835383262401528481L;

	public InvalidAppCodeException() {
		super();
	}

	public InvalidAppCodeException(String message) {
		super(message);
		LogUtil.getLogger(LOG_TYPE.OTHER).error(message + ExceptionLogUtil.log(this));
	}

	public InvalidAppCodeException(Throwable cause) {
		super(cause);
	}

	public InvalidAppCodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
