/**
 * Title:WxNsukeyCache.java
 * Author:riozenc
 * Datetime:2017年3月16日 下午6:14:55
**/
package sds.web.support.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WxNsukeyCache {
	private final static Map<String, NsukeyEntity> map = new HashMap<String, NsukeyEntity>();
	private final static Lock LOCK = new ReentrantLock();
	
	public static Map<String, NsukeyEntity> get(){
		return map;
	}

	public static boolean getValue(String key) {
		synchronized (key) {
			return map.get(key).getCount() == 3;
		}

	}

	public static void putNsukey(String key) {
		synchronized (key) {
			if (map.get(key) == null) {
				map.put(key, new NsukeyEntity(key));
			} else {
				map.get(key).setCount(map.get(key).getCount() + 1);
			}
			System.out.println(map.get(key).getCount());
		}
	}

	public static void removeKey(String key) {
		map.remove(key);
	}

}
