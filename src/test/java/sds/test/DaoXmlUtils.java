/**
 * 	Title:sds.test
 *		Datetime:2016年12月16日 下午5:19:13
 *		Author:czy
 */
package sds.test;

import java.io.IOException;

import com.riozenc.quicktool.common.util.ClassDAOXmlUtil;

import sds.webapp.acc.domain.UserDomain;

public class DaoXmlUtils {
	public static void main(String[] args) {
		// C:\Users\rioze\workspace\sds\src\main\java\sds\webapp\acc\dao
		try {
			ClassDAOXmlUtil.buildXML("C:\\Users\\rioze\\workspace\\sds\\src\\main\\java\\sds\\webapp\\acc\\dao", UserDomain.class,
					"fm_user");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
