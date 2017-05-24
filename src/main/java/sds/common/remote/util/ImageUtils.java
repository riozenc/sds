/**
 * Title:ImageUtils.java
 * Author:riozenc
 * Datetime:2017年5月23日 下午5:18:28
**/
package sds.common.remote.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.riozenc.quicktool.common.util.json.JSONUtil;

public class ImageUtils {
	/**
	 * 图片转为Base64字符串
	 * 
	 * @param filePath
	 * @return
	 */
	public static String GetImageStr(String filePath) {
		String[] strA = filePath.split("\\.");
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回Base64编码过的字节数组字符串
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("content", Base64Utils.encode(data));
		map.put("suffix", strA[strA.length - 1]);
		return JSONUtil.toJsonString(map);
	}
}
