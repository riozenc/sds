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

	private volatile static BaseProcessor instance = null;

	private BaseProcessor() {
	}

	public static BaseProcessor getInstance() {
		// 先检查实例是否存在，如果不存在才进入下面的同步块
		if (instance == null) {
			// 同步块，线程安全地创建实例
			synchronized (BaseProcessor.class) {
				// 再次检查实例是否存在，如果不存在才真正地创建实例
				instance = new BaseProcessor();
			}
		}
		return instance;
	}

	public void excute(BalanceEntity balanceEntity) {
		Lock lock = getLock();
		lock.lock();
		SqlSession sqlSession = SqlSessionManager.getSession();
		try {
			// 处理

			BalanceMerchantDomain balanceMerchantDomain = new BalanceMerchantDomain();
			balanceMerchantDomain.setTargetId(balanceEntity.getTargetId());
			balanceMerchantDomain.setAccount(balanceEntity.getAccount());
			balanceMerchantDomain.setBalance(balanceEntity.getAmount());
			balanceMerchantDomain.setUpdateDate(new Date());

			if (balanceEntity.getType() == 1) {
				// 入账
				balanceMerchantDomain.setRemark("入账");
				balanceMerchantDomain.setCountIn(balanceEntity.getAmount());
			} else {
				// 出账
				balanceMerchantDomain.setRemark("提现");
				balanceMerchantDomain.setCountOut(balanceEntity.getAmount());
			}

			balanceMerchantDomain.setStatus(1);
			int i = sqlSession.update("sds.webapp.blc.dao.BalanceMerchantDAO.update", balanceMerchantDomain);
			if (i == 0) {
				balanceMerchantDomain.setCountOut(new BigDecimal(0));
				balanceMerchantDomain.setCreateDate(new Date());
				sqlSession.insert("sds.webapp.blc.dao.BalanceMerchantDAO.insert", balanceMerchantDomain);
			}
			BalanceMerchantLogDomain balanceMerchantLogDomain = new BalanceMerchantLogDomain();
			balanceMerchantLogDomain.setTargetId(balanceEntity.getTargetId());
			balanceMerchantLogDomain.setAccount(balanceEntity.getAccount());
			balanceMerchantLogDomain.setBalance(balanceEntity.getAmount());
			balanceMerchantLogDomain.setCreateDate(new Date());
			balanceMerchantLogDomain.setUpdateDate(new Date());
			balanceMerchantLogDomain.setRemark(balanceEntity.getType() == 1 ? "入账" : "提现");
			balanceMerchantLogDomain.setOperation(balanceEntity.getType() == 1 ? "入账" : "提现");
			balanceMerchantLogDomain.setStatus(1);
			sqlSession.insert("sds.webapp.blc.dao.BalanceMerchantLogDAO.insert", balanceMerchantLogDomain);

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
