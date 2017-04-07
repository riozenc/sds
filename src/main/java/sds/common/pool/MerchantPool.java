package sds.common.pool;

import com.riozenc.quicktool.common.util.log.LogUtil;
import com.riozenc.quicktool.common.util.log.LogUtil.LOG_TYPE;

/**
 * 商户池 1、虚拟商户可靠性方法未完成 2、虚拟商户与真实数据是否建立关系方法未完成
 * 
 * @author riozenc
 *
 */
public class MerchantPool {
	private static MerchantPool pool = null;
	private final PoolState state = new PoolState(this);

	protected int poolTimeToWait = 20000;

	private MerchantPool() {

	}

	public static MerchantPool getInstance() {
		if (null == pool) {
			pool = new MerchantPool();
		}
		return pool;
	}

	public PoolBean getPoolBean(String account) {
		PoolBean poolBean = null;
		poolBean = state.activeBeansMap.get(account);
		return poolBean;
	}

	public PoolBean getPoolBean() throws Exception {
		PoolBean poolBean = null;
		while (poolBean == null) {
			synchronized (state) {
				if (!state.idleBeans.isEmpty()) {
					poolBean = state.idleBeans.remove(0);
				} else {
					if (!state.borrowBeans.isEmpty()) {

						for (PoolBean bean : state.borrowBeans) {
							if (System.currentTimeMillis() - bean.borrowedTimestamp > poolTimeToWait && bean.isOccupy()
									&& !bean.isBinding()) {
								bean.release();// 释放
								state.borrowBeans.remove(bean);// 移除借用列
								poolBean = bean;
								break;
							} else {
								state.badBeans.add(bean);
								bean = null;
							}
						}
						if (poolBean == null) {
							// 还有借用数据，等待
							state.wait(poolTimeToWait);
						}
					} else {
						// 无借用信息，申请补充
						// if (System.currentTimeMillis() - state.refreshTime <
						// state.refreshInterval) {
						// // 小于刷新间隔不予刷新
						// } else {
						// // 该刷新了
						// }
						LogUtil.getLogger(LOG_TYPE.MAIN).info("***************申请补充" + Thread.currentThread().getName());
						state.supplyIdleBeans();// 补充空闲
					}

				}
				if (poolBean != null) {
					if (poolBean.isValid()) {
						// poolState配置
						poolBean.occupy();
						state.borrowBeans.add(poolBean);
					} else {
						state.badBeans.add(poolBean);
						poolBean = null;
					}
				}
			}
		}
		return poolBean;
	}

	/**
	 * 已使用
	 * 
	 * @param poolBean
	 */
	public void use(PoolBean poolBean) {
		synchronized (state) {
			poolBean.occupy();// 占用标记
			state.borrowBeans.remove(poolBean);
			state.activeBeans.add(poolBean);
			state.activeBeansMap.put(poolBean.getRealObject().getAccount(), poolBean);
		}
	}

	/**
	 * 回收
	 * 
	 * @param poolBean
	 */
	public void recover(PoolBean poolBean) {
		synchronized (state) {
			if (poolBean.isOccupy()) {
				poolBean.release();// 释放占用标记
			}
			state.borrowBeans.remove(poolBean);// 移除借用列
			if (poolBean.isValid() && poolBean.recoverCount < 5) {
				state.idleBeans.add(poolBean);// 移到可用列
				state.notifyAll();// 唤醒
			} else {
				state.badBeans.add(poolBean);
			}
		}
	}

	/**
	 * 失效
	 * 
	 * @param poolBean
	 */
	public void disable(PoolBean poolBean) {
		synchronized (state) {
			state.idleBeans.remove(poolBean);
			state.borrowBeans.remove(poolBean);// 移除借用列
			state.activeBeans.remove(poolBean);
			state.activeBeansMap.remove(poolBean);
			state.badBeans.add(poolBean);
			poolBean = null;
		}
	}

	/**
	 * 池子状态信息（该方法是非线程安全的，结果会偏差）
	 * 
	 */
	public void readPoolState() {
		System.out.println(state.toString());
	}

}
