package sds.webapp.acc.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.exception.InvalidAppCodeException;
import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.pool.MerchantPool;
import sds.common.pool.PoolBean;
import sds.common.remote.RemoteResult;
import sds.common.remote.RemoteUtils;
import sds.common.remote.RemoteUtils.REMOTE_TYPE;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.acc.service.UserService;
import sds.webapp.sys.action.ConfAction;
import sds.webapp.sys.domain.ConfDomain;

@ControllerAdvice
@RequestMapping("merchant")
public class MerchantAction extends BaseAction {

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	/**
	 * 商户注册
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	// @RequestMapping(params = "type=register", method = RequestMethod.POST)
	@RequestMapping(params = "type=register")
	public String registerMerchant(MerchantDomain merchantDomain) throws Exception {
		String tjAccount = null;
		String appCode = merchantDomain.getAppCode();// 邀请码

		try {
			if (appCode.startsWith("EA")) {
				// 代理商
				tjAccount = appCode.substring(2);
				UserDomain param = new UserDomain();
				param.setAccount(tjAccount);
				param = userService.findByKey(param);
				merchantDomain.setAgentId(param.getId());// 建立代理商与商户关系
			} else if (appCode.startsWith("UA")) {
				// 商户
				tjAccount = appCode.substring(2);
				MerchantDomain param = new MerchantDomain();
				param.setAccount(tjAccount);
				param = merchantService.findByKey(param);
				merchantDomain.setTjId(param.getId());// 建立商户与商户关系
				merchantDomain.setAgentId(param.getAgentId());// 被推荐商户也属于推荐商户下的代理商
			} else {
				// 违法推荐码
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "无效的推荐码."));
			}
		} catch (NullPointerException e) {
			throw new InvalidAppCodeException("[" + appCode + "]无效的邀请码...");
		}

		// 远程注册
		if (merchantDomain.getUserType() == 2) {// 认证商户
			RemoteResult remoteResult = RemoteUtils.process(merchantDomain, REMOTE_TYPE.REGISTER);
			if (RemoteUtils.resultProcess(remoteResult)) {
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "注册失败[" + remoteResult.getMsg() + "]."));
			}
		} else {
			// 个人商户不注册，验卡时直接从账号池中获取
		}

		if (merchantService.findByKey(merchantDomain) == null) {
			merchantDomain.setStatus(0);// 审核中
			return insert(merchantDomain, "注册成功.", "注册失败.");
		} else {
			// 已经存在的手机号
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "已经存在的手机号."));
		}
	}

	/**
	 * 新增商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	public String insert(MerchantDomain merchantDomain, String success, String failed) {
		int i = merchantService.insert(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, success));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, failed));
		}
	}

	/**
	 * 删除商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=delete")
	public String delete(MerchantDomain merchantDomain) {
		int i = merchantService.delete(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "删除商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "删除商户失败"));
		}
	}

	/**
	 * 修改商户信息
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=update")
	public String update(MerchantDomain merchantDomain) {
		int i = merchantService.update(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新商户失败"));
		}
	}

	/**
	 * 根据条件查询商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findMerchantByWhere")
	public String findMerchantByWhere(MerchantDomain merchantDomain) {
		List<MerchantDomain> list = merchantService.findByWhere(merchantDomain);
		return JSONUtil.toJsonString(new JsonGrid(merchantDomain, list));
	}

	/**
	 * 根据条件查询商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=findMerchantByKey")
	public String findMerchantByKey(MerchantDomain merchantDomain) {
		return JSONUtil.toJsonString(merchantService.findByKey(merchantDomain));
	}

	/**
	 * 修改商户费率
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=updateRate")
	public String updateRate(MerchantDomain merchantDomain) throws Exception {
		RemoteResult remoteResult = null;
		if (merchantDomain.getUserType() == 2) {
			// 真实商户
			// 远程同步费率
			remoteResult = RemoteUtils.process(merchantDomain, REMOTE_TYPE.CHANGE_RATE);
		} else {
			// 个人商户
			try {
				List<MerchantDomain> vlist = merchantService.getVirtualMerchants(merchantDomain);
				if (vlist == null || vlist.size() == 0) {
					return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "该账户无法进行正常使用,请联系客服."));
				}
				for (MerchantDomain v : vlist) {
					v.setWxRate(merchantDomain.getWxRate());
					v.setAliRate(merchantDomain.getAliRate());
					remoteResult = RemoteUtils.process(v, REMOTE_TYPE.CHANGE_RATE);
					if (!RemoteUtils.resultProcess(remoteResult)) {
						return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
					}
				}
			} catch (Exception e) {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, e.getMessage()));
			}

		}
		if (RemoteUtils.resultProcess(remoteResult)) {
			int i = merchantService.updateRate(merchantDomain);
			if (i > 0) {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新商户成功."));
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新商户失败"));
			}
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
		}
	}

	/**
	 * 审核商户
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=checkMerchant")
	public String checkMerchant(MerchantDomain merchantDomain) throws Exception {

		Map<String, Object> map = merchantService.checkRate(merchantDomain.getId());

		Double cost_wrate = map.get("cost_wrate") == null ? 0 : (Double) map.get("cost_wrate");
		Double cost_arate = map.get("cost_arate") == null ? 0 : (Double) map.get("cost_arate");
		Double wx_rate = map.get("wx_rate") == null ? 0 : (Double) map.get("wx_rate");
		Double ali_rate = map.get("ali_rate") == null ? 0 : (Double) map.get("ali_rate");

		// 费率判断
		if (wx_rate < cost_wrate) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "微信费率错误"));
		}
		if (ali_rate < cost_arate) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "支付宝费率错误"));
		}

		try {
			if (merchantDomain.getUserType() != 2) {
				// ===根据等级进行分配虚拟账户
				ConfDomain confDomain = ConfAction.getConfig(ConfAction.MERCHANT_LEVEL_COUNT)
						.get(merchantDomain.getLevel().toString());
				for (int i = Integer.valueOf(confDomain.getValue()); i > 0; i--) {
					PoolBean bean = MerchantPool.getInstance().getPoolBean();
					bean.binding(merchantDomain);
				}
			}
		} catch (Exception e) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, e.getMessage()));
		}

		// 判断费率
		merchantDomain.setStatus(3);// 3审核成功
		merchantService.update(merchantDomain);

		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "审核商户成功."));
	}

	/**
	 * 下载密钥
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=downLoadKey")
	public String downLoadKey(MerchantDomain merchantDomain) throws Exception {

//		MerchantDomain temp = UserUtils.getPrincipal().getMerchantDomain();
		 MerchantDomain temp = merchantService.findByKey(merchantDomain);
		if (temp.getUserType() == 2) {// 认证商户
			RemoteResult remoteResult = RemoteUtils.process(temp, REMOTE_TYPE.DOWNLOAD_KEY);
			if (RemoteUtils.resultProcess(remoteResult)) {
				temp.setPrivatekey(remoteResult.getPrivatekey());
				int i = merchantService.update(temp);
				if (i > 0) {
					return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "下载密钥成功."));
				} else {
					return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新密钥失败."));
				}
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
			}
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "个人商户无需下载密钥."));
		}
	}

	/**
	 * 验卡
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=validCard")
	public String validCard(MerchantDomain merchantDomain) throws Exception {
		MerchantDomain temp = UserUtils.getPrincipal().getMerchantDomain();
		merchantDomain.setId(temp.getId());
		RemoteResult remoteResult = null;
		if (temp.getUserType() == 2) {// 认证商户
			// 正常验卡
			remoteResult = RemoteUtils.process(merchantDomain, REMOTE_TYPE.VALID_CARD);
		} else {
			// 特殊验卡
			remoteResult = RemoteUtils.process(merchantDomain, REMOTE_TYPE.SPECIAL_VALID_CARD);
		}

		if (RemoteUtils.resultProcess(remoteResult)) {
			int i = merchantService.update(temp);
			if (i > 0) {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "验卡成功."));
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新验卡数据失败."));
			}
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
		}
	}
}
