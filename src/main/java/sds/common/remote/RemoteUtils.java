package sds.common.remote;

import sds.common.remote.domain.DownloadKeyDomain;
import sds.common.remote.domain.RegisterDomain;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.sys.action.ConfAction;
import sds.webapp.sys.domain.ConfDomain;

public class RemoteUtils {
	public enum REMOTE_TYPE {
		REGISTER, DOWNLOAD_KEY, CHANGE_RATE, GET_A_CODE_PAY
	}

	public static RemoteResult process(MerchantDomain merchantDomain, REMOTE_TYPE remoteType) {
		RemoteResult remoteResult = null;
		try {
			switch (remoteType) {
			case REGISTER:// 注册
				remoteResult = RemoteHandler.register(new RegisterDomain(merchantDomain.getAccount()));
				break;
			case DOWNLOAD_KEY:// 下载密钥
				remoteResult = RemoteHandler.downLoadKey(new DownloadKeyDomain(merchantDomain.getAccount()));
				break;
			case CHANGE_RATE:// 修改费率
				remoteResult = changeRate(merchantDomain);
				break;

			case GET_A_CODE_PAY:// 获取一码付
				remoteResult = getACodePay(merchantDomain);
				break;
			}
			return remoteResult;
		} catch (Exception e) {
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}

	}

	/**
	 * 验卡
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 * @info 关联虚拟账号
	 */
	public static RemoteResult validCard(MerchantDomain merchantDomain, MerchantDomain proxyMerchantDomain)
			throws Exception {

		proxyMerchantDomain.setRealName(merchantDomain.getRealName());
		proxyMerchantDomain.setCmer(merchantDomain.getCmer());
		proxyMerchantDomain.setCmerSort(merchantDomain.getCmerSort());
		proxyMerchantDomain.setPhone(merchantDomain.getPhone());
		proxyMerchantDomain.setBusinessId(merchantDomain.getBusinessId());
		proxyMerchantDomain.setChannelCode(merchantDomain.getChannelCode());
		proxyMerchantDomain.setCardNo(merchantDomain.getCardNo());
		proxyMerchantDomain.setCertNo(merchantDomain.getCertNo());
		proxyMerchantDomain.setMobile(merchantDomain.getMobile());
		proxyMerchantDomain.setLocation(merchantDomain.getLocation());
		proxyMerchantDomain.setCertCorrect(merchantDomain.getCertCorrect());
		proxyMerchantDomain.setCertOpposite(merchantDomain.getCertOpposite());
		proxyMerchantDomain.setCertMeet(merchantDomain.getCertMeet());
		proxyMerchantDomain.setCardCorrect(merchantDomain.getCardCorrect());
		proxyMerchantDomain.setCardOpposite(merchantDomain.getCardOpposite());

		return RemoteHandler.validCard(proxyMerchantDomain);
	}

	/**
	 * 更换结算卡
	 * 
	 * @param merchantDomain
	 * @param proxyMerchantDomain
	 * @return
	 * @throws Exception
	 */
	public static RemoteResult replaceCard(MerchantDomain merchantDomain, MerchantDomain proxyMerchantDomain)
			throws Exception {

//		proxyMerchantDomain.setRealName(merchantDomain.getRealName());
		proxyMerchantDomain.setCardNo(merchantDomain.getCardNo());
		proxyMerchantDomain.setCertNo(merchantDomain.getCertNo());
		proxyMerchantDomain.setMobile(merchantDomain.getMobile());
		proxyMerchantDomain.setPhone(merchantDomain.getPhone());
		proxyMerchantDomain.setLocation(merchantDomain.getLocation());

		return RemoteHandler.validCard(proxyMerchantDomain);
	}

	/**
	 * 修改费率
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	private static RemoteResult changeRate(MerchantDomain merchantDomain) throws Exception {
		return RemoteHandler.changeRate(merchantDomain);
	}

	/**
	 * 正扫支付
	 * 
	 * @param merchantDomain
	 * @param amount
	 * @param info
	 * @param channelCode
	 * @return
	 */
	public static RemoteResult pay(MerchantDomain merchantDomain, int amount, String info, int channelCode) {

		try {
			return RemoteHandler.pay(merchantDomain.getAccount(), merchantDomain.getPrivatekey(), amount, channelCode,
					info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	/**
	 * 反扫
	 * 
	 * @param merchantDomain
	 * @param amount
	 * @param channelCode
	 * @param productName
	 * @param productDetail
	 * @param authCode
	 * @return
	 */
	public static RemoteResult scanPay(MerchantDomain merchantDomain, int amount, int channelCode, String productName,
			String productDetail, String authCode) {

		try {
			return RemoteHandler.scanPay(merchantDomain.getAccount(), merchantDomain.getPrivatekey(), amount,
					channelCode, productName, productDetail, authCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	/**
	 * 订单状态查询
	 * 
	 * @param merchantDomain
	 * @param orderId
	 * @return
	 */
	public static RemoteResult orderConfirm(MerchantDomain merchantDomain, String orderId) {
		try {
			return RemoteHandler.orderConfirm(merchantDomain.getAccount(), merchantDomain.getPrivatekey(), orderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	/**
	 * 一码付（柜台码）
	 * 
	 * @param merchantDomain
	 * @return
	 */
	public static RemoteResult getACodePay(MerchantDomain merchantDomain) {
		try {
			return RemoteHandler.getACodePay(merchantDomain.getAccount(), merchantDomain.getPrivatekey());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	public static boolean resultProcess(RemoteResult remoteResult) throws Exception {
		ConfDomain confDomain = ConfAction.getConfig(ConfAction.REMOTE_RESULT).get(remoteResult.getRespCode());
		remoteResult.setMsg(confDomain.getRemark());
		if ("000000".equals(remoteResult.getRespCode())) {
			return true;
		}

		// 999999 SYSTEMERROR 系统异常
		// 100000 REGISTERSYSTEMERROR 注册系统异常
		// 200000 ERROR 获取密钥系统异常
		// 300000 ERROR 报备(同步)商户系统异常
		// 400000 ERROR 验卡系统异常
		// 500000 ERROR 正扫交易系统异常
		// 600000 ERROR 反扫交易系统异常
		// 700000 ERROR 订单确认系统异常
		// (0)
		// 000001 ERRORPARAMS 参数有误
		// 000002 ERRORNOTEXISTORPASS 商户不存在或者密码错误
		// 000003 ERRORORDER 订单号错误
		// 000004 ERRORDECRYPT 解密失败
		// 000005 ERRORORDERCODE OrderCode参数错误
		// 000006 ERRORKEY 密钥有误
		// 000007 CHANNELlERROR 通道错误
		// 000008 ERRORAUTOGRAPH 签名验证失败
		// (1)
		// 100001 ERRORCBZID CBZID错误
		// 100002 ERRORCODEORCBZID CBZID或推荐码错误
		// 100003 ERRORCPWD 密码错误
		// 100004 ERRORALREADYREGISTERED 账号已经注册
		// 100005 ERRORALREADYREGISTERED 账号已经注册
		// 100006 ERRORACCOUNTFORMAT 注册帐号格式错误(手机号码)
		// 100007 REGISTERFAIL 账号注册失败
		// 100008 ERRORPASSWORD 注册密码格式错误(6-12位)
		// 100009 ERRORCODE 推荐码错误
		// (2)
		//
		// (3)
		// 300001 ERRORNOTAUDITED 商户未审核
		// 300002 ERRORNOCHECKCARD 商户未验卡
		// 300003 ERRORAUDITING 审核中不允许修改
		// 300004 ERRORSYNC 同步中不允许修改
		// 300005 ERRORRATE 签约费率错误
		// 300006 ERROR 同步商户签约费率异常
		// 300007 ERRORCMRSTATE 商户审核状态异常
		// 300008 ERRORADDCMRFAIL 报备商户失败
		// 300009 ERRORUPCMRFAIL 同步商户失败
		// 399999 ERROR 超过报备商户限制(小时)
		// (4)
		// 400001 ERRORCHECKCARD 验卡失败
		// 400002 UNSUPPORTED_BILLING_CARD 验卡通道不支持的结算卡
		// (5)
		// 500001 WAITPAY 等待支付
		// 500002 ERROR 刷卡支付报文信息有误
		// 500003 ERRORAMOUNT 金额有误
		// 500004 USERPAYING 刷卡支付用户需要输入支付密码
		// 500005 ERROR 刷卡支付失败
		// 500006 ERROR 商户正扫关闭
		// 599999 ERROR 正扫交易关闭
		// (6)
		// 600001 ERROR 商户反扫关闭
		// 699999 ERROR 反扫扫交易关闭
		// 000000 SUCCESS 成功

		return false;
	}

}
