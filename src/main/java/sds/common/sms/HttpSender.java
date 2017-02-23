package sds.common.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @param url
 *            应用地址，类似于http://ip:port/msg/
 * @param un
 *            账号
 * @param pw
 *            密码
 * @param phone
 *            手机号码，多个号码使用","分割
 * @param msg
 *            短信内容
 * @param rd
 *            是否需要状态报告，需要1，不需要0
 * @return 返回值定义参见HTTP协议文档
 * @throws Exception
 */
public class HttpSender {

	public static String send(String account) {
		String url = "http://sms.253.com/msg/send";// 应用地址
		String un = "N5030495";// 账号
		String pw = "8424888bB";// 密码
		String phone = account;// 手机号码，多个号码使用","分割
		String code = getRandom();
		String msg = "【253云通讯】您好，你的验证码是" + code;// 短信内容
		String rd = "1";// 是否需要状态报告，需要1，不需要0
		String ex = null;// 扩展码

		try {
			String returnString = HttpSender.batchSend(url, un, pw, phone, msg, rd, ex);
			SmsCache.put(account, code);
			return returnString;
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
			return null;
		}

	}

	public static String batchSend(String url, String un, String pw, String phone, String msg, String rd, String ex)
			throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("un", un));
		params.add(new BasicNameValuePair("pw", pw));
		params.add(new BasicNameValuePair("phone", phone));
		params.add(new BasicNameValuePair("rd", rd));
		params.add(new BasicNameValuePair("msg", msg));
		params.add(new BasicNameValuePair("ex", ex));

		// 转换为键值对
		String str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
		System.out.println(url + "?" + str);
		// 创建Get请求
		HttpGet httpGet = new HttpGet(url + "?" + str);

		try {
			// 执行Get请求，
			CloseableHttpResponse response = httpClient.execute(httpGet);
			// 得到响应体
			HttpEntity entity = response.getEntity();
			String jsonStr = EntityUtils.toString(entity);// , "utf-8");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return jsonStr;
			} else {
				throw new Exception("HTTP ERROR Status: " + jsonStr);
			}
		} finally {
			httpGet.releaseConnection();
		}
	}

	private static String getRandom() {
		return Integer.toString((int)((Math.random() * 9 + 1) * 100000));
	}
	
	public static void main(String[] args) {
		System.out.println(getRandom());
	}

}