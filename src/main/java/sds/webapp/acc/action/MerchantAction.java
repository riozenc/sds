package sds.webapp.acc.action;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.common.security.util.UserUtils;
import sds.common.webapp.base.action.BaseAction;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;
import sds.webapp.acc.service.MerchantService;
import sds.webapp.acc.service.UserService;
import sds.webapp.acc.util.Base64;
import sds.webapp.acc.util.LocalUtil;
import sds.webapp.acc.util.Base64Utils;
import sds.webapp.acc.util.Common;
import sds.webapp.acc.util.RSAUtils;
import sds.webapp.acc.util.MyURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riozenc.quicktool.common.util.json.JSONUtil;

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
	 * 注册商户
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=register", method = RequestMethod.POST)
	public String registerMerchant(MerchantDomain merchantDomain) {
		String account = merchantDomain.getAccount();// 手机号
		String appCode = merchantDomain.getAppCode();// 邀请码

		if (appCode.startsWith("EA")) {
			// 代理商
			account = appCode.substring(2);
			UserDomain param = new UserDomain();
			param.setAccount(account);
			param = userService.findByKey(param);
			merchantDomain.setAgentId(param.getId());// 建立代理商与商户关系
		} else if (appCode.startsWith("UA")) {
			// 商户
			account = appCode.substring(2);
			MerchantDomain param = new MerchantDomain();
			param.setAccount(account);
			param = merchantService.findByKey(param);
			merchantDomain.setTjId(param.getId());// 建立商户与商户关系
			merchantDomain.setAgentId(param.getAgentId());// 被推荐商户也属于推荐商户下的代理商
		} else {
			// 违法推荐码
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "无效的推荐码."));
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
	 * 修改商户费率
	 * 
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=updateRate")
	public String updateRate(MerchantDomain merchantDomain) {
		int i = merchantService.updateRate(merchantDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "更新商户成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "更新商户失败"));
		}
	}

	/**
	 * 验卡
	 * @param merchantDomain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "type=validCard")
	public String validCard(MerchantDomain merchantDomain){
		if(merchantDomain.getUserType() == 2){ 
			//企业商户
			String account = UserUtils.getPrincipal().getMerchantDomain().getAccount();
			String PRIVATEKEY = UserUtils.getPrincipal().getMerchantDomain().getPrivatekey();
			Map<String,String> map = new HashMap<String,String>();
			map.put("orderCode","tb_verifyInfo");
			map.put("account", account);
			
			Map<String,String> msgData = new HashMap<String,String>();
			msgData.put("real_name", Base64.encodeToString(merchantDomain.getRealName()));
			msgData.put("cmer", Base64.encodeToString(merchantDomain.getCmer()));
			msgData.put("cmer_sort", Base64.encodeToString(merchantDomain.getCmerSort()));
			msgData.put("phone",merchantDomain.getPhone());
			msgData.put("business_id",merchantDomain.getBusinessId());
			msgData.put("channel_code","WXPAY");
			msgData.put("card_type", "1" );
			msgData.put("card_no", merchantDomain.getCardNo());
			msgData.put("cert_type", "00");
			msgData.put("cert_no",merchantDomain.getCertNo());
			msgData.put("mobile", merchantDomain.getMobile());
			msgData.put("location", Base64.encodeToString(merchantDomain.getLocation()));
			msgData.put("cert_correct", "");
			msgData.put("cert_opposite", "");
			msgData.put("cert_meet","");
			msgData.put("card_correct", "");
			msgData.put("card_opposite","");
			String msgJson = JSONUtil.toJsonString(msgData);
			//签名
            byte[] sign = LocalUtil.sign(Base64.decode(PRIVATEKEY.getBytes()), msgJson);
            map.put("msg",msgJson);
            String mapJson = JSONUtil.toJsonString(map);
            String data = Base64.encodeToString(mapJson);

            // 加密
         	PublicKey publicKey;
			try {
				publicKey = RSAUtils.loadPublicKey(Common.PUBLICKKEY);
				byte[] decryptByte1 = RSAUtils.encryptData(data.getBytes(),publicKey);
				System.out.println("加密:"+ Base64Utils.encode(decryptByte1));
				Map<String,String> mapGlobal = new HashMap<String,String>();
				mapGlobal.put("signature",new String(sign));
				mapGlobal.put("data", Base64Utils.encode(decryptByte1));
				String request = JSONUtil.toJsonString(mapGlobal);
				byte[] res = MyURLConnection.postBinResource(Common.URL,request,Common.CHARSET,30);
		        String response = new String(res,"UTF-8");
		       //需要解析返回json
		        
		        
		        System.out.println("返回json"+response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         	
         	
		}
		return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "验卡成功."));
		
	}
}
