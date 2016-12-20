package sds.webapp.sys.action;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@RequestMapping("test")
public class TestAction {

	@RequestMapping(params = "type=test")
	public String test() {
		return "datagrid-demo.html";
	}

	@ResponseBody
	@RequestMapping(params = "type=getJson")
	public String getJson(String id) {
		System.out.println(id);
		return "[{\"sex\":true,\"code\":\"531212\",\"opercode\":\"000514\",\"operdate\":\"2014-01-01 08:02:30\",\"seedate\":\"2014-01-01 08:15:31\",\"regfee\":2.00,\"order\":1,\"see\":true,\"deptcode\":\"1031\",\"regdate\":\"2014-01-01 08:02:30\",\"name\":\"许**\",\"age\":\"1953\",\"fr\":true,\"regname\":\"普通号\",\"deptname\":\"内科\"}]";
	}
}
