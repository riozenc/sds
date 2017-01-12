/**
 * 
 */
package sds.webapp.acc.util;

import com.riozenc.quicktool.config.Global;


/**
 * @author zj
 * 基类
 * @date:2016年4月8日上午9:50:36
 */
public class Common {
	static String jCode = Global.getConfig("Code");
	static String jCBZID = Global.getConfig("CBZID");
	public static final String CHARSET = "UTF-8";
	// 公钥
//	public static final String PUBLICKKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg7pwBwcQWYEF72LAXZapEgIfIQB5NY3RVcKLF7/mbClEt5x3QODh2ttCtL/SI2rdrvGcyqsMlTCX44TkqZaqfP3KLxRjJ4qvURpWKxC7z/uIFC+lRumzxnhJqLIOC13kf42MUWgg5sKHnA3XQqlXRPdX1ZJ/lK+a2d5F0H8tW9uJiqqpfC1qY/fkiPuBh0XgiCHZmqj7VcrLg4P+p0lDmoyXHFFDmQG22rj1TAzcn855Ebdt4vnXENH3fLP3rSE4bCKxkrmZ3AUr9cNhpx4tFbiRl7Tzv3lLPquzHKu9gFdkImkcra0EYREZKw6kUUmXcpxvxSBt0hzpoqr1L5X6JwIDAQAB";
	public static final String PUBLICKKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg7pwBwcQWYEF72LAXZapEgIfIQB5NY3RVcKLF7/mbClEt5x3QODh2ttCtL/SI2rdrvGcyqsMlTCX44TkqZaqfP3KLxRjJ4qvURpWKxC7z/uIFC+lRumzxnhJqLIOC13kf42MUWgg5sKHnA3XQqlXRPdX1ZJ/lK+a2d5F0H8tW9uJiqqpfC1qY/fkiPuBh0XgiCHZmqj7VcrLg4P+p0lDmoyXHFFDmQG22rj1TAzcn855Ebdt4vnXENH3fLP3rSE4bCKxkrmZ3AUr9cNhpx4tFbiRl7Tzv3lLPquzHKu9gFdkImkcra0EYREZKw6kUUmXcpxvxSBt0hzpoqr1L5X6JwIDAQAB";
	// Key
	public static final String KEY = "BD161A60C8933E7EC1D1B802376D6245";
	// code
	public static final String Code = jCode;
	// cbzid
	public static final String CBZID = jCBZID;
	// 请求URL
//	public static final String URL = "http://mf.branding.chinavalleytech.com/ChannelConn/Kubei";
	public static final String URL = "http://mf.branding.chinavalleytech.com/ChannelConn/Kubei";
	
//	public static final String URL = "";
	// 批量注册接口
//	public static final String REGISTERURL="";
	// 实时注册接口
//	public static final String REGISTERURL="";
	// 实时注册接口
		public static final String REGISTERURL="http://mf.branding.chinavalleytech.com/ChannelConn/rlregister";
		
}
