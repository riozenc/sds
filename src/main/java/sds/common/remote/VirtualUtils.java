package sds.common.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.riozenc.quicktool.mybatis.db.SqlSessionManager;

import sds.webapp.acc.domain.MerchantDomain;

/**
 * 虚拟商户
 * 
 * @author riozenc
 *
 */
public class VirtualUtils {

	private static List<MerchantDomain> findVirtualMerchants(MerchantDomain merchantDomain) {
		SqlSession sqlSession = SqlSessionManager.getSession();
		List<MerchantDomain> list = sqlSession.selectList("sds.webapp.acc.dao.MerchantDAO.findVirtualMerchants",
				merchantDomain);
		return list;
	}

	public static List<MerchantDomain> merchantVirtual(MerchantDomain merchantDomain) {
		List<MerchantDomain> list = null;
		if (merchantDomain.getUserType() == 2) {// 认证商户
			list = new ArrayList<MerchantDomain>();
		} else {// 个人商户
			list = findVirtualMerchants(merchantDomain);
		}
		return list;
	}
}
