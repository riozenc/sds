package sds.webapp.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sds.webapp.sys.domain.ConfDomain;
import sds.webapp.sys.service.ConfService;

@ControllerAdvice
@RequestMapping("config")
public class ConfAction {

	public static final String REMOTE_RESULT = "REMOTE_RESULT";// 远程结果编码
	public static final String MERCHANT_LEVEL_COUNT = "MERCHANT_LEVEL_COUNT";// 商户等级数量

	private static final Map<String, Map<String, ConfDomain>> MAP = new ConcurrentHashMap<String, Map<String, ConfDomain>>();
	private static boolean FLAG = false;

	@Autowired
	@Qualifier("confServiceImpl")
	private ConfService confService;

	public static Map<String, ConfDomain> getConfig(String type) throws Exception {
		long time = System.currentTimeMillis();
		while (!FLAG) {
			if ((System.currentTimeMillis() - time) > 10 * 1000) {
				// 等待10秒
				throw new Exception("获取参数超时.");
			}
		}
		return MAP.get(type);
	}

	// *************************
	public String initConf(String type) {
		ConfDomain confDomain = new ConfDomain();
		confDomain.setType(type);
		List<ConfDomain> list = confService.findByWhere(confDomain);
		Map<String, ConfDomain> map = MAP.get(type);
		if (map == null) {
			map = new HashMap<String, ConfDomain>();
		}
		map.clear();
		for (ConfDomain temp : list) {
			map.put(temp.getName(), temp);
		}
		return "finish";
	}

	// 初始化远程结果参数
	@ResponseBody
	@RequestMapping(params = "type=initRemoteResult")
	public String initRemoteResultConfig() {

		ConfDomain confDomain = new ConfDomain();
		confDomain.setType(REMOTE_RESULT);
		List<ConfDomain> list = confService.findByWhere(confDomain);
		Map<String, ConfDomain> map = MAP.get(REMOTE_RESULT);
		if (map == null) {
			map = new HashMap<String, ConfDomain>();
		}
		map.clear();
		for (ConfDomain temp : list) {
			map.put(temp.getName(), temp);
		}
		return "finish";

	}

	@ResponseBody
	@RequestMapping(params = "type=reflush")
	public String reflush() {

		return null;
	}
}
