package sds.common.json;

public class JsonResult {
	public static final int SUCCESS = 200;
	public static final int ERROR = 300;

	private Integer statusCode;
	private String message;

	public JsonResult(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
