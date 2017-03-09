/**
 * Title:QueueuManager.java
 * Author:riozenc
 * Datetime:2017年3月3日 下午2:09:01
**/
package sds.common.queue.manager;

/**
 * 队列管理器
 * 备注：容量固定，缺乏动态修改热部署
 */
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sds.common.queue.BaseQueue;
import sds.common.queue.entity.BalanceEntity;

public class QueueManager {
	private final static int LIST_SIZE = 5;
	private final static int LIST_LIMIT = 1000;
	protected Long LIMIT_TIME = 1 * 60 * 1000L;// 1分钟
	protected LinkedList<BaseQueue> list = new LinkedList<BaseQueue>();
	private final ExecutorService executor = Executors
			.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());// 获取cpu个数

	private volatile static QueueManager instance = null;

	private QueueManager() {
		System.out.println("初始化");
		for (int i = 0; i < LIST_SIZE; i++) {
			list.add(new BaseQueue(this, LIST_LIMIT));
		}
	}

	public static QueueManager getInstance() {
		// 先检查实例是否存在，如果不存在才进入下面的同步块
		if (instance == null) {
			// 同步块，线程安全地创建实例
			synchronized (QueueManager.class) {
				// 再次检查实例是否存在，如果不存在才真正地创建实例
				instance = new QueueManager();
			}
		}
		return instance;
	}

	/**
	 * 推送任务
	 * 
	 * @param balanceEntity
	 */
	public final void pushTask(BalanceEntity balanceEntity) {
		executor.execute(offerTask(balanceEntity));
	}

	private BaseQueue offerTask(BalanceEntity balanceEntity) {
		BaseQueue queue = null;
		while (queue == null) {
			synchronized (this) {
				queue = getQueue();
				if (queue.offerVO(balanceEntity)) {
					System.out.println(queue + "放入" + balanceEntity);
				} else {
					queue = null;
					switchQueue();// 切换
				}
			}
		}
		return queue;
	}

	private BaseQueue getQueue() {
		BaseQueue queue = list.peekFirst();
		queue.setLastUsedTimestamp(System.currentTimeMillis());
		return queue;
	}

	private void switchQueue() {
		list.add(list.remove());
	}

}
