package sds.common.exception;

import com.riozenc.quicktool.common.util.log.ExceptionLogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

/**
 * 池子资源枯竭
 * 
 * @author riozenc
 *
 */
public class PoolResourceExhaustionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334008054403795675L;

	public PoolResourceExhaustionException() {
		super();
	}

	public PoolResourceExhaustionException(String message) {
		super(message);
		LogUtil.getLogger(LOG_TYPE.OTHER).error(message + ExceptionLogUtil.log(this));
	}

	public PoolResourceExhaustionException(Throwable cause) {
		super(cause);
	}

	public PoolResourceExhaustionException(String message, Throwable cause) {
		super(message, cause);
	}

}
