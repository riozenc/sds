/**
 * Title:NsukeyEntity.java
 * Author:riozenc
 * Datetime:2017年3月20日 上午10:24:50
**/
package sds.web.support.wx;

public class NsukeyEntity {
	private String key;
	private int count;

	public NsukeyEntity(String key) {
		this.key = key;
		this.count = 1;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
