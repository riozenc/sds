package sds.webapp.sys.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sds.webapp.ord.domain.OrderDomain;
import sds.webapp.stm.service.ProfitService;

@ControllerAdvice
@RequestMapping("test")
public class TestAction {

	@Autowired
	@Qualifier("profitServiceImpl")
	private ProfitService profitService;

	@RequestMapping(params = "type=test")
	public String test() {

		OrderDomain orderDomain = new OrderDomain();
		orderDomain.setOrderId("XX0110992007268636");
		orderDomain.setChannelCode(1);
		orderDomain.setRespInfo("000000");
		orderDomain.setRespCode("SUCCESS");
		orderDomain.setAmount(20000d);
		orderDomain.setDate(new Date());
		orderDomain.setAccount("18660509556");
		orderDomain.setProxyAccount("19900000016");
		orderDomain.setRemark("测试");
		orderDomain.setStatus(1);
		profitService.profit(orderDomain);
		
		System.out.println("------------------");
		profitService.test();
		profitService.test();

		return null;
	}

	@ResponseBody
	@RequestMapping(params = "type=getJson")
	public String getJson(String id) {
		System.out.println(id);
		return "[{\"sex\":true,\"code\":\"531212\",\"opercode\":\"000514\",\"operdate\":\"2014-01-01 08:02:30\",\"seedate\":\"2014-01-01 08:15:31\",\"regfee\":2.00,\"order\":1,\"see\":true,\"deptcode\":\"1031\",\"regdate\":\"2014-01-01 08:02:30\",\"name\":\"许**\",\"age\":\"1953\",\"fr\":true,\"regname\":\"普通号\",\"deptname\":\"内科\"}]";
	}

}
