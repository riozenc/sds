package sds.common.pool;

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

	public PoolBean getPoolBean() throws Exception {
		PoolBean poolBean = null;
		while (poolBean == null) {
			synchronized (state) {
				if (!state.idleBeans.isEmpty()) {
					poolBean = state.idleBeans.remove(0);
				} else {
					if (!state.borrowBeans.isEmpty()) {

						for (PoolBean bean : state.borrowBeans) {
							if (System.currentTimeMillis() - bean.borrowedTimestamp > poolTimeToWait * 2
									&& bean.isOccupy()) {
								bean.release();// 释放
								state.borrowBeans.remove(bean);// 移除借用列
								poolBean = bean;
								break;
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
						System.out.println("***************申请补充" + Thread.currentThread().getName());
						state.supplyIdleBeans();// 补充空闲
					}

				}
				if (poolBean != null) {
					if (poolBean.isValid()) {
						// poolState配置
						poolBean.occupy();
						state.borrowBeans.add(poolBean);
					} else {
						if (testRealValid(poolBean)) {// 该方法未完成
							state.activeBeans.add(poolBean);
						} else {
							state.badBeans.add(poolBean);
						}
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
	public void used(PoolBean poolBean) {
		synchronized (state) {
			poolBean.occupy();// 占用标记
			state.borrowBeans.remove(poolBean);
			state.activeBeans.add(poolBean);
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
			if (poolBean.isValid()) {
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
			state.badBeans.add(poolBean);
			poolBean = null;
		}
	}

	/**
	 * 池子状态信息（该方法是非线程安全的，结果会偏差）
	 * 
	 * @param l
	 */
	public void readPoolState() {
		System.out.println(state.toString());
	}

	/**
	 * 测试真实有效性
	 * 
	 * @param poolBean
	 * @return
	 */
	private boolean testRealValid(PoolBean poolBean) {
		return true;
	}
}
