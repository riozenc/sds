package sds.common.jgpush;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

public class Jpush {
	protected static final Logger LOG = LogUtil.getLogger(LOG_TYPE.IO);
	private static final String appKey = "810793943f1676930b73f7fb";
	private static final String masterSecret = "b35846014c8eacbda6e3f340";
	public static final String ALERT = "5345345223423";// 会推送出这个常量
	public static final String MSG_CONTENT = "极光推送测试";
	public static final String TITLE = "测试字段-极光推送";
	public static final JPushClient jpushClient = new JPushClient(masterSecret, appKey);

//	 public static void main(String[] args){
//	 SendPush("123","看看","瞅瞅");//第一个参数是别名，第二个参数是推送内容，第三个参数是消息头
//	 }
	public static void SendPush(String account,String message,String cmer,String orderId,String amount) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date.getTime());
        Map<String, String> extras = new HashMap<String, String>();  
        // 添加附加信息  
        extras.put("cmer", cmer);
        extras.put("amount", amount);
        extras.put("orderId", orderId);
        extras.put("data", time.toString());
        PushPayload payload = buildPushObject_all_alias_Message(account,message, extras);  
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}
	//商户审核的方法
	public static void SendPushSH(String alias, String alert, String title) {
        PushPayload payload = buildPushObject_all(alias,alert,title);  
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}
	//自定义推送消息
	public static PushPayload buildPushObject_all_alias_Message(String alias,String message,  
            Map<String, String> extras)  {  
        return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
        .setAudience(Audience.alias(alias))// 根据别名推送
            .setMessage(Message.newBuilder().setMsgContent(message).addExtras(extras).build())  
            //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发    
            .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();  
    }  
	// 广播
	public static PushPayload buildPushObject_all_alias_alert(String str) {

		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.all())// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.alert(str)).build();
	}

	// 根据别名
	public static PushPayload buildPushObject_all(String alias, String alert, String title) {
		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.alias(alias))// 根据别名推送
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build()).build())
				.build();
	}

	// 安卓别名推送
	public static PushPayload buildAndroidWinphonePayload(String alias, String alert, String title) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build()).build())
				.build();
	}

	// ios别名推送
	public static PushPayload buildIOSPayload(String alias, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.ios_winphone()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert).build()).build();
	}
	
	
}
