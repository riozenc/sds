/**
 * Title:BaseProcessor.java
 * Author:riozenc
 * Datetime:2017年3月3日 下午1:59:14
**/
package sds.common.queue.processor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.session.SqlSession;

import com.riozenc.quicktool.mybatis.db.SqlSessionManager;

import sds.common.queue.entity.BalanceEntity;
import sds.webapp.blc.domain.BalanceMerchantDomain;
import sds.webapp.blc.domain.BalanceMerchantLogDomain;

/**
 * 最终实现：多个处理器，但是账户锁通用一个。 目前实现：一个处理器
 * 
 * @author riozenc
 *
 */
public class BaseProcessor {

	private static Lock lock = new ReentrantLock();// 锁

	private volatile static BaseProcessor instance = new BaseProcessor();

	private BaseProcessor() {
	}

	public static BaseProcessor getInstance() {
		return instance;
	}

	public void excute(BalanceEntity balanceEntity) {
		Lock lock = getLock();
		lock.lock();

		// 交易日志
		BalanceMerchantLogDomain balanceMerchantLogDomain = new BalanceMerchantLogDomain();
		balanceMerchantLogDomain.setTargetId(balanceEntity.getTargetId());
		balanceMerchantLogDomain.setAccount(balanceEntity.getAccount());
		balanceMerchantLogDomain.setOrderId(balanceEntity.getOrderId());
		balanceMerchantLogDomain.setOrderDate(balanceEntity.getOrderDate());
		balanceMerchantLogDomain.setCreateDate(new Date());
		balanceMerchantLogDomain.setUpdateDate(new Date());
		balanceMerchantLogDomain.setStatus(balanceEntity.getType());

		// 交易实例
		BalanceMerchantDomain balanceMerchantDomain = new BalanceMerchantDomain();
		balanceMerchantDomain.setTargetId(balanceEntity.getTargetId());
		balanceMerchantDomain.setAccount(balanceEntity.getAccount());
		balanceMerchantDomain.setBalance(balanceEntity.getAmount());
		balanceMerchantDomain.setUpdateDate(new Date());

		if (balanceEntity.getType() == 1) {
			// 入账
			balanceMerchantDomain.setRemark("入账");
			balanceMerchantDomain.setCountIn(balanceEntity.getAmount());
			balanceMerchantLogDomain.setRemark("入账");
			balanceMerchantLogDomain.setBalance(balanceEntity.getAmount());
			balanceMerchantLogDomain.setOperation("入账");
			compute(balanceMerchantDomain, balanceMerchantLogDomain);
		} else if (balanceEntity.getType() == 2) {
			// 出账
			balanceMerchantDomain.setRemark("提现");
			balanceMerchantDomain.setCountOut(balanceEntity.getAmount());
			balanceMerchantLogDomain.setRemark("提现");
			balanceMerchantLogDomain.setBalance(balanceEntity.getAmount().negate());
			balanceMerchantLogDomain.setOperation("提现");
			compute(balanceMerchantDomain, balanceMerchantLogDomain);
		} else if (balanceEntity.getType() == 3) {
			balanceMerchantLogDomain.setStatus(1);// 算为入账
			balanceMerchantLogDomain.setRemark("重算");
			balanceMerchantLogDomain.setBalance(balanceEntity.getAmount());
			balanceMerchantLogDomain.setOperation("重算");

			recalculation(balanceMerchantLogDomain);
			// 让对应订单的利润失效
			// 添加新的订单利润
			// 计算余额

		}
	}

	private void compute(BalanceMerchantDomain balanceMerchantDomain,
			BalanceMerchantLogDomain balanceMerchantLogDomain) {
		SqlSession sqlSession = SqlSessionManager.getSession();
		try {
			balanceMerchantDomain.setStatus(1);
			int i = sqlSession.update("sds.webapp.blc.dao.BalanceMerchantDAO.update", balanceMerchantDomain);
			if (i == 0) {
				balanceMerchantDomain.setCountOut(new BigDecimal(0));
				balanceMerchantDomain.setCreateDate(new Date());
				sqlSession.insert("sds.webapp.blc.dao.BalanceMerchantDAO.insert", balanceMerchantDomain);
			}
			sqlSession.insert("sds.webapp.blc.dao.BalanceMerchantLogDAO.insert", balanceMerchantLogDomain);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
		} finally {
			sqlSession.close();
			lock.unlock();
		}

	}

	private void recalculation(BalanceMerchantLogDomain balanceMerchantLogDomain) {
		SqlSession sqlSession = SqlSessionManager.getSession();
		try {
			// 更新
			sqlSession.update("sds.webapp.blc.dao.BalanceMerchantLogDAO.invalidBalanceLog", balanceMerchantLogDomain);
			// 新增
			sqlSession.insert("sds.webapp.blc.dao.BalanceMerchantLogDAO.insert", balanceMerchantLogDomain);
			// 更新
			sqlSession.update("sds.webapp.blc.dao.BalanceMerchantLogDAO.computeByLog", balanceMerchantLogDomain);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
		} finally {
			sqlSession.close();
			lock.unlock();
		}
	}

	private Lock getLock() {

		return lock;
	}
}
