/**
 * Title:NoticeAction.java
 * Author:riozenc
 * Datetime:2017年2月26日 上午10:42:48
**/
package sds.webapp.sys.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riozenc.quicktool.common.util.json.JSONUtil;

import sds.common.json.JsonGrid;
import sds.common.json.JsonResult;
import sds.webapp.sys.domain.NoticeDomain;
import sds.webapp.sys.service.NoticeService;

@ControllerAdvice
@RequestMapping("notice")

public class NoticeAction {

	@Autowired
	@Qualifier("noticeServiceImpl")
	private NoticeService noticeService;

	@ResponseBody
	@RequestMapping(params = "type=insert")
	public String insert(NoticeDomain noticeDomain) {
		int i = noticeService.insert(noticeDomain);
		if (i > 0) {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.SUCCESS, "发布成功."));
		} else {
			return JSONUtil.toJsonString(new JsonResult(JsonResult.ERROR, "发布失败."));
		}
	}

	@ResponseBody
	@RequestMapping(params = "type=findNoticeByWhere")
	public String findNoticeByWhere(NoticeDomain noticeDomain) {
		List<NoticeDomain> list = noticeService.findByWhere(noticeDomain);
		return JSONUtil.toJsonString(new JsonGrid(noticeDomain, list));
	}
}
