package sds.webapp.sys.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sds.common.pool.MerchantPool;
import sds.common.pool.PoolBean;
import sds.webapp.acc.domain.MerchantDomain;

@ControllerAdvice
@RequestMapping("test")
public class TestAction {

	@RequestMapping(params = "type=test")
	public String test() {
		MerchantPool.getInstance();

		ExecutorService executorService = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 1600; i++) {
			int l = i;
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					MerchantPool merchantPool = MerchantPool.getInstance();
					PoolBean poolBean;
					try {
						poolBean = merchantPool.getPoolBean();
						MerchantDomain merchantDomain = poolBean.getObject();

						System.out.println(merchantDomain.hashCode() + "(" + merchantDomain.getId() + ")" + "[" + l
								+ "]" + Thread.currentThread().getName());

						if (l == 888 || l == 1111) {
							poolBean.used();// 占用
						} else {
							poolBean.recover();
						}

						if (l % 10 == 0) {
							MerchantPool.getInstance().readPoolState();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		executorService.shutdown();
		return null;
	}

	@ResponseBody
	@RequestMapping(params = "type=getJson")
	public String getJson(String id) {
		System.out.println(id);
		return "[{\"sex\":true,\"code\":\"531212\",\"opercode\":\"000514\",\"operdate\":\"2014-01-01 08:02:30\",\"seedate\":\"2014-01-01 08:15:31\",\"regfee\":2.00,\"order\":1,\"see\":true,\"deptcode\":\"1031\",\"regdate\":\"2014-01-01 08:02:30\",\"name\":\"许**\",\"age\":\"1953\",\"fr\":true,\"regname\":\"普通号\",\"deptname\":\"内科\"}]";
	}

}
