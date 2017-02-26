package sds.common.pool;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.riozenc.quicktool.common.util.date.DateUtil;
import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;
import com.riozenc.quicktool.mybatis.db.SqlSessionManager;
import com.riozenc.quicktool.springmvc.context.SpringContextHolder;

import sds.common.exception.PoolResourceExhaustionException;
import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.service.MerchantService;

public class PoolState {
	protected long refreshTime;// 刷新时间
	protected long refreshInterval = 20 * 1000;// 刷新间隔

	private long errorCount = 0;// 错误次数
	private long recoveryCount = 0;// 恢复次数

	protected final static int NOT_USED = 0;// 未使用
	protected final static int TO_USED = 1;// 待使用
	protected final static int USED = 2;// 已使用
	protected final static int DISABLE = 3;// 失效

	protected MerchantPool pool;

	protected boolean valid = true;//

	protected final List<PoolBean> idleBeans = new ArrayList<PoolBean>();// 未使用
	protected final List<PoolBean> borrowBeans = new ArrayList<PoolBean>();// 借用中
	protected final List<PoolBean> activeBeans = new ArrayList<PoolBean>();// 使用中
	protected final Map<String, PoolBean> activeBeansMap = new HashMap<String, PoolBean>();// 使用中
	protected final List<PoolBean> badBeans = new ArrayList<PoolBean>();// 失效
	protected final Map<Integer, PoolBean> poolRelMap = new HashMap<Integer, PoolBean>();

	private RowBounds rowBounds = new RowBounds(1, 10);// offset, limit

	public PoolState(MerchantPool pool) {
		this.pool = pool;
		this.refreshTime = System.currentTimeMillis();
		init();// 初始化
	}

	/**
	 * 不可用
	 */
	private void error() {
		valid = false;
		errorCount++;
	}

	/**
	 * 重启
	 */
	private void recovery() {
		valid = true;
		recoveryCount++;
	}

	private void init() {
		SqlSession sqlSession = SqlSessionManager.getSession();
		List<MerchantDomain> list = sqlSession.selectList("sds.webapp.acc.dao.MerchantDAO.findIdleByPool", TO_USED);
		for (MerchantDomain temp : list) {
			PoolBean poolBean = new PoolBean(temp, pool);
			idleBeans.add(poolBean);
		}
		sqlSession.close();
	}

	/**
	 * 补充(同步数据)
	 * 
	 * @throws Exception
	 */
	public void supplyIdleBeans() throws Exception {
		LogUtil.getLogger(LOG_TYPE.OTHER).info("* * * * 补充数据 * * * *");
		MerchantService merchantService = SpringContextHolder.getBean("merchantServiceImpl");
		synActiveBeans(merchantService);
		synIdleBeans(merchantService);
		synBadBeans(merchantService);
	}

	private void synActiveBeans(MerchantService merchantService) {
		if (!activeBeans.isEmpty()) {
			LogUtil.getLogger(LOG_TYPE.OTHER).info("* * * * 同步已使用数据(" + activeBeans.size() + ") * * * *");
			for (PoolBean temp : activeBeans) {
//				merchantService.validCard(temp.getRealObject(), temp.getObject());绑卡
				
//				merchantService.updatePool(temp.getObject());
//				if (merchantService.updatePoolRel(temp.getRealObject().getId(), temp.getObject().getId()) == 0) {
//					merchantService.insertPoolRel(temp.getRealObject().getId(), temp.getObject().getId());
//				}
			}
			activeBeans.clear();
			activeBeansMap.clear();
		}
	}

	private void synIdleBeans(MerchantService merchantService) throws Exception {
		SqlSession sqlSession = SqlSessionManager.getSession();
		List<MerchantDomain> list = sqlSession.selectList("sds.webapp.acc.dao.MerchantDAO.findIdleByPool", NOT_USED,
				this.rowBounds);
		LogUtil.getLogger(LOG_TYPE.OTHER).info("* * * * 补充可用数据(" + list.size() + ") * * * *");
		if (list == null || list.isEmpty()) {
			error();
			throw new PoolResourceExhaustionException("暂无可用虚拟商户...");
		} else {
			if (!valid) {
				recovery();
			}
		}
		for (MerchantDomain temp : list) {
			temp.setStatus(TO_USED);// 1:待使用
			PoolBean poolBean = new PoolBean(temp, pool);
			idleBeans.add(poolBean);
		}
		sqlSession.close();
		if (!list.isEmpty()) {
			for (MerchantDomain temp : list) {
				merchantService.updatePool(temp);
			}
		}
	}

	private void synBadBeans(MerchantService merchantService) throws Exception {
		if (!badBeans.isEmpty()) {
			LogUtil.getLogger(LOG_TYPE.OTHER).info("* * * * 同步失效数据(" + badBeans.size() + ") * * * *");
			for (PoolBean temp : badBeans) {
				merchantService.updatePool(temp.getObject());
			}
			badBeans.clear();
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("\n===CONFINGURATION==============================================");
		builder.append("\n ---STATUS-----------------------------------------------------");
		builder.append("\n idleBeans               ").append(idleBeans.size());
		builder.append("\n borrowBeans               ").append(borrowBeans.size());
		builder.append("\n activeBeans               ").append(activeBeans.size());
		builder.append("\n badBeans             ").append(badBeans.size());
		builder.append("\n errorCount             ").append(errorCount);
		builder.append("\n recoveryCount             ").append(recoveryCount);
		builder.append("\n time                 ").append("[" + DateUtil.formatDateTime(new Date()) + "]");
		builder.append("\n===============================================================");
		return builder.toString();
	}
}
