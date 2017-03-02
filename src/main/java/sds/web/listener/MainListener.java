/**
 * 	Title:sds.web.listener
 *		Datetime:2016年12月16日 上午10:41:59
 *		Author:czy
 */
package sds.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_OUT_TYPE;
import com.riozenc.quicktool.mybatis.db.DbFactory;

import sds.common.timmer.ProfitCountTimerTask;
import sds.common.timmer.TimmerUtils;

public class MainListener implements ServletContextListener {
	/**
	 * 启动
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		// 日志方式
		LogUtil.setLogOutType(LOG_OUT_TYPE.FILE);// 全部日志根据文件配置输出
		// LogUtil.setLogOutType(LOG_OUT_TYPE.SYSTEM);// 全部日志根据文件配置输出

		DbFactory.initByFactory();

		
		//启动定时任务
		TimmerUtils.run(new ProfitCountTimerTask());
		
		
		// 初始化conf

		System.out.println("initialized");

	}

	/**
	 * 销毁
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("destory");
	}
}
