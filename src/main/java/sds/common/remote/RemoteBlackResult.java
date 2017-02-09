package sds.common.remote;

/**
 * 加密结果
 * 
 * @author riozenc
 *
 */
public class RemoteBlackResult {
	private String data;
	private String count;
	private String signature;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
