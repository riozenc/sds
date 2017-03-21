/**
 * Title:SmsCache.java
 * Author:riozenc
 * Datetime:2017年2月27日 下午2:24:29
**/
/**
 * Title:SmsCache.java
 * Author:riozenc
 * Datetime:2017年2月22日 下午7:11:24
**/
package sds.common.sms;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 需要每天清理
 * 
 * @author riozenc
 *
 */
public class SmsCache {

	private static final Map<String, SmsEntity> map = new ConcurrentHashMap<>();

	public static String get(String name) {
		if (map.get(name) != null) {
			if (System.currentTimeMillis() - map.get(name).createTime > 5 * 60 * 1000) {
				map.get(name).code = null;
			}
			return map.get(name).code;
		} else {
			return null;
		}
	}

	public static void put(String key, String value) {
		map.put(key, new SmsEntity(value));
	}

	public static void remove(String key) {
		map.remove(key);
	}

	public static void clear() {
		for (Entry<String, SmsEntity> entry : map.entrySet()) {
			if (System.currentTimeMillis() - entry.getValue().createTime > 60 * 60 * 24 * 1000) {
				map.remove(entry.getKey());
				continue;
			}
			if (System.currentTimeMillis() - entry.getValue().createTime > 60 * 60 * 1000) {
				entry.getValue().code = null;
			}
		}
	}

	private static class SmsEntity {
		protected String code;
		protected long createTime;

		SmsEntity(String code) {
			this.code = code;
			createTime = System.currentTimeMillis();
		}

	}

	public static void main(String[] args) {
		SmsCache.put("1", "A");
		SmsCache.put("2", "B");
		SmsCache.put("3", "C");

		System.out.println(SmsCache.get("2"));

		for (Entry<String, SmsEntity> entry : map.entrySet()) {
			if (entry.getKey().equals("1")) {
				map.remove(entry.getKey());
				continue;
			}
			if (System.currentTimeMillis() - entry.getValue().createTime > 60 * 60 * 1000) {
				entry.getValue().code = null;
			}
		}

	}
}
