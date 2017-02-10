package sds.common.remote;

import sds.common.pool.MerchantPool;
import sds.common.pool.PoolBean;
import sds.common.remote.domain.DownloadKeyDomain;
import sds.common.remote.domain.RegisterDomain;
import sds.common.security.util.UserUtils;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.sys.action.ConfAction;
import sds.webapp.sys.domain.ConfDomain;

public class RemoteUtils {
	public enum REMOTE_TYPE {

		REGISTER, DOWNLOAD_KEY, VALID_CARD, CHANGE_RATE, SPECIAL_VALID_CARD
	}

	public static RemoteResult process(MerchantDomain merchantDomain, REMOTE_TYPE remoteType) {
		RemoteResult remoteResult = null;
		try {
			switch (remoteType) {
			case REGISTER:
				remoteResult = RemoteHandler.register(new RegisterDomain(merchantDomain.getAccount()));
				break;
			case DOWNLOAD_KEY:
				remoteResult = RemoteHandler.downLoadKey(new DownloadKeyDomain(merchantDomain.getAccount()));
				break;
			case VALID_CARD:
				remoteResult = RemoteHandler.validCard(merchantDomain);
				break;
			case CHANGE_RATE:
				remoteResult = RemoteHandler.changeRate(merchantDomain);
				break;
			case SPECIAL_VALID_CARD:
				remoteResult = specialValidCard(merchantDomain);
			}
			return remoteResult;
		} catch (Exception e) {
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}

	}

	/**
	 * 虚拟账户验卡
	 * 
	 * @param merchantDomain
	 * @return
	 * @throws Exception
	 */
	private static RemoteResult specialValidCard(MerchantDomain merchantDomain) throws Exception {
		PoolBean bean = MerchantPool.getInstance().getPoolBean();
		MerchantDomain proxyMerchantDomain = bean.getObject();
		// 替换结算卡
		proxyMerchantDomain.setRealName(merchantDomain.getRealName());
		proxyMerchantDomain.setMobile(merchantDomain.getMobile());
		proxyMerchantDomain.setCardNo(merchantDomain.getCardNo());
		proxyMerchantDomain.setCertNo(merchantDomain.getCertNo());
		RemoteResult remoteResult = RemoteUtils.process(proxyMerchantDomain, REMOTE_TYPE.VALID_CARD);
		// 回收bean
		bean.recover();
		return remoteResult;
	}

	public static RemoteResult pay(MerchantDomain merchantDomain, double amount, String info, int channelCode) {

		checkPrivateKey(merchantDomain.getPrivatekey());
		try {
			return RemoteHandler.pay(merchantDomain.getAccount(), merchantDomain.getPrivatekey(), amount, channelCode,
					info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	public static RemoteResult scanPay(MerchantDomain merchantDomain, double amount, int channelCode,
			String productName, String productDetail, String authCode) {
		checkPrivateKey(merchantDomain.getPrivatekey());
		try {
			return RemoteHandler.scanPay(merchantDomain.getAccount(), merchantDomain.getPrivatekey(), amount,
					channelCode, productName, productDetail, authCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	public static RemoteResult orderConfirm(String orderId) {
		MerchantDomain merchantDomain = UserUtils.getPrincipal().getMerchantDomain();
		checkPrivateKey(merchantDomain.getPrivatekey());
		try {
			return RemoteHandler.orderConfirm(merchantDomain.getAccount(), merchantDomain.getPrivatekey(), orderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RemoteResult("999999", e.getClass().getName());
		}
	}

	// public static MerchantDomain checkMerchant(MerchantDomain merchantDomain)
	// {
	// if (merchantDomain.getUserType() != 2) {// 非认证商户（个人商户）
	// merchantDomain = MerchantPool.getInstance().getPoolBean().getObject();//
	// 全部随机
	// }
	// return merchantDomain;
	// }

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

	private static void checkPrivateKey(String privateKey) {
		if (privateKey == null) {
			throw new RuntimeException("请先下载密钥...");
		}
	}

}
