package sds.webapp.acc.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.riozenc.quicktool.common.util.file.FileUtil;
import com.riozenc.quicktool.common.util.json.JSONUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;
import com.riozenc.quicktool.config.Global;

import sds.common.exception.InvalidAppCodeException;
import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.pool.MerchantPool;
import sds.common.pool.PoolBean;
import sds.common.remote.RemoteResult;
import sds.common.remote.RemoteUtils;
import sds.common.remote.RemoteUtils.REMOTE_TYPE;
import sds.common.security.util.UserUtils;
import sds.common.sms.SmsCache;
import sds.common.sms.SmsSender;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.acc.service.UserService;

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
	 * 获取注册验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=getRegisterVerificationCode")
	public String getRegisterVerificationCode(String account) {

		return JSONUtil.toJsonString(SmsSender.send(account));
	}

	/**
	 * 获取验证用户的的验证码
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=getVerificationCode")
	public String getVerificationCode(String account) {
		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setAccount(account);
		merchantDomain = merchantService.findByKey(merchantDomain);
		if (merchantDomain == null) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "不是有效的账户."));
		}
		return JSONUtil.toJsonString(SmsSender.send(account));
	}

	/**
	 * 重置密码
	 * 
	 * @param account
	 * @param password
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=resetPassword")
	public String resetPassword(String account, String password, String code) {
		String vc = SmsCache.get(account);
		if (vc == null || !vc.equals(code)) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "无效的验证码."));
		}
		MerchantDomain merchantDomain = new MerchantDomain();
		merchantDomain.setAccount(account);
		merchantDomain.setPassword(password);

		return update(merchantDomain);
	}

	/**
	 * 商户注册
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 * @info 所有注册均跟账户池中的虚拟账户关联，只需注册到本系统就可以
	 */
	@ResponseBody
	@RequestMapping(params = "type=register", method = RequestMethod.POST)
	public String registerMerchant(MerchantDomain merchantDomain, String code) throws Exception {
		String tjAccount = null;
		String appCode = merchantDomain.getAppCode();// 邀请码

		String vc = SmsCache.get(merchantDomain.getAccount());
		if (vc == null || !vc.equals(code)) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "无效的验证码."));
		}
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

		if (merchantService.findByKey(merchantDomain) == null) {
			merchantDomain.setStatus(0);// 审核中
			int i = merchantService.register(merchantDomain);
			if (i > 0) {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "注册成功."));
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "注册失败."));
			}
		} else {
			// 已经存在的手机号
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "已经存在的手机号."));
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

		// 此ID为userID
		merchantDomain.setId(UserUtils.getPrincipal().getUserDomain().getId());
		List<MerchantDomain> list = merchantService.findMerchantByUser(merchantDomain);
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
		UserUtils.getPrincipal();// 用于测试自动登录
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

		Map<String, Object> map = merchantService.checkRate(merchantDomain.getId());
		// 代理商成本费率
		Double cost_wrate = map.get("cost_wrate") == null ? 0 : (Double) map.get("cost_wrate");
		Double cost_arate = map.get("cost_arate") == null ? 0 : (Double) map.get("cost_arate");
		// 商户费率
		Double wx_rate = map.get("wx_rate") == null ? 0 : (Double) map.get("wx_rate");
		Double ali_rate = map.get("ali_rate") == null ? 0 : (Double) map.get("ali_rate");

		// 费率判断
		if (wx_rate < cost_wrate) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "微信费率错误"));
		}
		if (ali_rate < cost_arate) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "支付宝费率错误"));
		}

		MerchantDomain temp = merchantService.getVirtualMerchant(merchantDomain);
		if (temp == null) {
			throw new Exception("该账户无法进行正常使用,请检查系统.");
		}

		temp.setWxRate(merchantDomain.getWxRate());
		temp.setAliRate(merchantDomain.getAliRate());
		RemoteResult remoteResult = RemoteUtils.process(temp, REMOTE_TYPE.CHANGE_RATE);
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
	 * params:id
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=checkMerchant")
	public String checkMerchant(MerchantDomain merchantDomain) throws Exception {
		UserDomain userDomain = UserUtils.getPrincipal().getUserDomain();

		// merchantDomain = merchantService.findByKey(merchantDomain);

		try {
			MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);

			// 同步费率
			vm.setWxRate(userDomain.getUserArate());
			vm.setAliRate(userDomain.getUserArate());
			RemoteResult remoteResult = RemoteUtils.process(vm, REMOTE_TYPE.CHANGE_RATE);
			if (RemoteUtils.resultProcess(remoteResult)) {
				merchantDomain.setWxRate(userDomain.getUserArate());
				merchantDomain.setAliRate(userDomain.getUserWrate());
				LogUtil.getLogger(LOG_TYPE.OTHER).info(merchantDomain.getAccount() + "[" + vm.getAccount() + "]"
						+ "费率同步成功(" + userDomain.getUserArate() + "," + userDomain.getUserWrate() + ").");
			} else {
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, remoteResult.getMsg()));
			}

			// if (merchantDomain.getUserType() != 2) {
			// // ===根据等级进行分配虚拟账户
			// ConfDomain confDomain =
			// ConfAction.getConfig(ConfAction.MERCHANT_LEVEL_COUNT)
			// .get(merchantDomain.getLevel().toString());
			// for (int i = Integer.valueOf(confDomain.getValue()); i > 0; i--)
			// {
			// PoolBean bean = MerchantPool.getInstance().getPoolBean();
			// bean.binding(merchantDomain);
			// }
			// } else {
			// PoolBean bean = MerchantPool.getInstance().getPoolBean();
			// bean.binding(merchantDomain);
			// }
		} catch (Exception e) {
			e.printStackTrace();
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
	 * @info 批量注册时已经下载好密钥，此功能暂时无用
	 */
	@ResponseBody
	@RequestMapping(params = "type=downLoadKey")
	public String downLoadKey(MerchantDomain merchantDomain) throws Exception {

		// MerchantDomain temp = UserUtils.getPrincipal().getMerchantDomain();
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
	 * @info 区分
	 */
	@ResponseBody
	@RequestMapping(params = "type=validCard")
	public String validCard(MerchantDomain merchantDomain, HttpServletRequest request) throws Exception {
		MerchantDomain temp = UserUtils.getPrincipal().getMerchantDomain();
		merchantDomain.setId(temp.getId());
		merchantDomain.setStatus(1);

		// 真实商户绑定虚拟账户
		PoolBean bean = null;
		RemoteResult remoteResult = null;
		try {
			bean = MerchantPool.getInstance().getPoolBean();

			// 为分配的虚拟账号进行验卡
			remoteResult = RemoteUtils.validCard(merchantDomain, bean.getObject());
			if (RemoteUtils.resultProcess(remoteResult)) {
				// 绑定成功
				bean.binding(merchantDomain);// 绑定处理
				merchantService.validCard(merchantDomain, bean.getObject());

				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
						request.getSession().getServletContext());
				if (multipartResolver.isMultipart(request)) {
					// 将request变成多部分request
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
					// 获取multiRequest 中所有的文件名
					Iterator<String> iter = multiRequest.getFileNames();
					while (iter.hasNext()) {
						String name = iter.next();
						// 一次遍历所有文件
						MultipartFile file = multiRequest.getFile(name);
						if (file != null) {
							// 上传
							file.transferTo(FileUtil.createFile(Global.getConfig("file.doc.path"),
									temp.getAccount() + "_" + name));
						}
					}
				}

				return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "验卡成功."));
			} else {
				// 绑定失败
				bean.recover();
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR,
						remoteResult.getMsg() + "[" + remoteResult.getRespInfo() + "]"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (bean != null) {
				bean.recover();
			}
			e.printStackTrace();
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新验卡数据失败(" + e + ")."));
		}

	}

	/**
	 * 更换结算卡
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=replaceCard")
	public String replaceCard(MerchantDomain merchantDomain) {
		MerchantDomain vm = merchantService.getVirtualMerchant(merchantDomain);
		RemoteResult remoteResult = null;
		try {
			// 替换结算卡
			remoteResult = RemoteUtils.validCard(merchantDomain, vm);
			if (RemoteUtils.resultProcess(remoteResult)) {
				// 替换成功
				merchantService.validCard(merchantDomain, vm);
				return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "替换成功."));
			} else {
				// 绑定失败
				return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR,
						remoteResult.getMsg() + "[" + remoteResult.getRespInfo() + "]"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新验卡数据失败(" + e + ")."));
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(params = "type=upload")
	public void upload(HttpServletRequest request) throws IllegalStateException, IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				String name = iter.next();
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(name);
				if (file != null) {
					// 上传
					file.transferTo(FileUtil.createFile(Global.getConfig("file.doc.path"),
							UserUtils.getPrincipal().getMerchantDomain().getAccount() + "_" + name));
				}
			}
		}
	}

	/**
	 * base64 转图片
	 * 
	 * @param base64Data
	 * @param request
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "type=base64Upload")
	public void base64Upload(String base64Data, HttpServletRequest request) throws Exception {
		String dataPrix = "";
		String data = "";

		if (base64Data == null || "".equals(base64Data)) {
			throw new Exception("上传失败，上传图片数据为空");
		} else {
			String[] d = base64Data.split("base64,");
			if (d != null && d.length == 2) {
				dataPrix = d[0];
				data = d[1];
			} else {
				throw new Exception("上传失败，数据不合法");
			}
		}

		String suffix = "";
		if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
			suffix = ".jpg";
		} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {// data:image/x-icon;base64,base64编码的icon图片数据
			suffix = ".ico";
		} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {// data:image/gif;base64,base64编码的gif图片数据
			suffix = ".gif";
		} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
			suffix = ".png";
		} else {
			throw new Exception("上传图片格式不合法");
		}

		File file = FileUtil.createFile(Global.getConfig("file.doc.path"),
				UserUtils.getPrincipal().getMerchantDomain().getAccount() + "_" + System.currentTimeMillis() + suffix);

		byte[] bs = Base64Utils.decodeFromString(data);

		// for (int i = 0; i < bs.length; ++i) {
		// if (bs[i] < 0) {
		// bs[i] += 256;
		// }
		// }
		try {
			// 使用apache提供的工具类操作流
			FileUtils.writeByteArrayToFile(file, bs);
		} catch (Exception ee) {
			throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
		}

		OutputStream out = new FileOutputStream(file);
		out.write(bs);
		out.flush();
		out.close();

	}
}
