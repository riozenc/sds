/**
 * Title:ExcelUtils.java
 * Author:riozenc
 * Datetime:2017年3月8日 下午3:32:30
**/
package sds.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.riozenc.quicktool.common.util.date.DateUtil;

import sds.webapp.stm.domain.ProfitDomain;
import sds.webapp.stm.domain.ProfitUserDomain;
import sds.webapp.stm.domain.WithdrawalsDomain;

public class ExcelUtils {

	private static final String[] COLUMN_NAME = { "#", "收款人姓名", "收款人帐号", "开户行", "支行", "联行号", "通道", "支付金额" };
	private static final String[] COLUMN_NAME_EXCEL = {"#","商户号","提现金额","提现人","提现时间"};
	public static void export(List<ProfitUserDomain> list, HttpServletResponse httpServletResponse) throws IOException {
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("sheet");
		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		HSSFRow row1 = sheet.createRow(0);
		for (int i = 0; i < COLUMN_NAME.length; i++) {
			row1.createCell(i).setCellValue(COLUMN_NAME[i]);
		}

		for (int i = 0; i < list.size(); i++) {
			HSSFRow temp = sheet.createRow(i + 1);

			temp.createCell(0).setCellValue(i + 1);
			temp.createCell(1).setCellValue(list.get(i).getJsName());// 姓名
			temp.createCell(2).setCellValue(list.get(i).getJsCard());
			temp.createCell(3).setCellValue(list.get(i).getJsBank());
			temp.createCell(4).setCellValue(list.get(i).getJsBankadd());
			temp.createCell(5).setCellValue(list.get(i).getJsLhno());
			temp.createCell(6).setCellValue("");
			temp.createCell(7).setCellValue(list.get(i).getTotalProfit());

		}
		String fileName = "清算文件" + "_" + DateUtil.formatDate(new Date());
		// wb.write(stream);
		// 输出Excel文件
		OutputStream output = httpServletResponse.getOutputStream();
		httpServletResponse.reset();
		httpServletResponse.setHeader("Content-disposition",
				"attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
		httpServletResponse.setContentType("application/msexcel");
		wb.write(output);
		output.close();
	}
	public static void exportExcel(List<WithdrawalsDomain> list, HttpServletResponse httpServletResponse) throws IOException {
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("sheet");
		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		HSSFRow row1 = sheet.createRow(0);
		for (int i = 0; i < COLUMN_NAME_EXCEL.length; i++) {
			row1.createCell(i).setCellValue(COLUMN_NAME_EXCEL[i]);
		}

		for (int i = 0; i < list.size();i++) {
			HSSFRow temp = sheet.createRow(i + 1);

			temp.createCell(0).setCellValue(i + 1);
			temp.createCell(1).setCellValue(list.get(i).getAccount());
			temp.createCell(2).setCellValue(list.get(i).getAmount());
			temp.createCell(3).setCellValue(list.get(i).getRealName());
			temp.createCell(4).setCellValue(DateUtil.formatDate(list.get(i).getDate()));

		}
		String fileName = "余额明细" + "_" + DateUtil.formatDate(new Date());
		// wb.write(stream);
		// 输出Excel文件
		OutputStream output = httpServletResponse.getOutputStream();
		httpServletResponse.reset();
		httpServletResponse.setHeader("Content-disposition",
				"attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
		httpServletResponse.setContentType("application/msexcel");
		wb.write(output);
		output.close();
	}
}
