/**
 * Title:SmsSender.java
 * Author:riozenc
 * Datetime:2017年2月27日 下午2:29:50
**/
package sds.common.sms;

import java.util.ArrayList;
import java.util.Date;
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

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

public class SmsSender {

	private final static String url = "http://sms.253.com/msg/send";
	private final static String un = "N4054934";
	private final static String pw = "9PoAcNKBsb2da8";
	private final static String msg = "【马付】您好，您的验证码是";

	public static String send(String account) {
		try {
			String code = getRandom();
			String returnString = SmsSender.batchSend(url, un, pw, account, msg + code, "1", null);
			SmsCache.put(account, code);
			return returnString;
		} catch (Exception e) {
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
			String jsonStr = EntityUtils.toString(entity, Consts.UTF_8);
			LogUtil.getLogger(LOG_TYPE.OTHER).info(DateUtil.formatDateTime(new Date()) + " [" + phone + "] " + jsonStr
					+ " (" + response.getStatusLine().getStatusCode() + ") ");
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
		return Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
	}

	public static void main(String[] args) {
		System.out.println(SmsSender.send("18660509556"));
	}
}
