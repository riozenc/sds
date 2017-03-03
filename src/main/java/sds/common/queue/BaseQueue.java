/**
 * Title:BaseQueue.java
 * Author:riozenc
 * Datetime:2017年3月3日 上午11:57:47
**/
package sds.common.queue;

import java.util.concurrent.BlockingQueue;

import sds.common.queue.processor.BaseProcessor;

/**
 * 抽象队列
 * 
 * @author riozenc
 *
 */
public abstract class BaseQueue<E> {
	// 数据队列
	protected BlockingQueue<E> blockingQueue;

	// 处理器
	protected BaseProcessor baseProcessor;

	// 有效标志
	private boolean isValid;
	// 创建时间
	private long createTimestamp;//
	// 最后使用时间
	private long lastUsedTimestamp;//

	public abstract void run();
	
	

}
