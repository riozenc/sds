/**
 * Title:ProfitCountTimerTask.java
 * Author:riozenc
 * Datetime:2017年2月27日 下午8:16:07
**/
package sds.common.timmer;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;
import com.riozenc.quicktool.springmvc.context.SpringContextHolder;

import sds.webapp.stm.action.ProfitAction;
import sds.webapp.stm.domain.ProfitDomain;

public class ProfitCountTimerTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ProfitAction profitAction = SpringContextHolder.getBean(ProfitAction.class);
		ProfitDomain profitDomain = new ProfitDomain();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		profitDomain.setOrderDate(calendar.getTime());

		profitAction.profitCount(profitDomain);

		LogUtil.getLogger(LOG_TYPE.OTHER).info(DateUtil.formatDateTime(new Date()) + "执行前一天的分润统计");
	}

}
