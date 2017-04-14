package sds.common.pool;

import sds.webapp.acc.domain.MerchantDomain;

public class PoolBean {

	private MerchantPool merchantPool;
	private MerchantDomain virtualMerchant;
	private MerchantDomain realMerchant;
	private boolean valid;
	private boolean used;
	private boolean binding;
	private long createdTimestamp;
	private long lastUsedTimestamp;
	protected int recoverCount;// 失败回收次数
	protected long borrowedTimestamp;// 借用时间

	public PoolBean(MerchantDomain merchantDomain, MerchantPool merchantPool) {
		this.virtualMerchant = merchantDomain;
		this.merchantPool = merchantPool;
		this.valid = true;
		this.binding = false;
		this.createdTimestamp = System.currentTimeMillis();
		this.lastUsedTimestamp = System.currentTimeMillis();
	}

	/**
	 * 失效
	 */
	protected void disabled() {
		valid = false;
	}

	protected void enabled() {
		valid = true;
	}

	/**
	 * 占用
	 */
	protected void occupy() {
		used = true;
		borrowedTimestamp = System.currentTimeMillis();
	}

	/**
	 * 释放
	 */
	protected void release() {
		used = false;
		borrowedTimestamp = System.currentTimeMillis();
	}

	public boolean isOccupy() {
		return used;
	}

	public boolean isBinding() {
		return binding && realMerchant != null;
	}

	/**
	 * 判断有效性 有效&&未占用&未绑定&状态未使用or待使用=treue
	 * 
	 * @return
	 */
	public boolean isValid() {
		return valid && !isOccupy() && !isBinding() && (virtualMerchant.getStatus() == PoolState.NOT_USED
				|| virtualMerchant.getStatus() == PoolState.TO_USED);
	}

	public MerchantDomain getObject() {
		return virtualMerchant;
	}

	public MerchantDomain getRealObject() {
		return realMerchant;
	}

	/**
	 * 回收
	 * 目前只有验卡失败才会回收
	 */
	public void recover() {
		this.recoverCount++;
		merchantPool.recover(this);
	}

	/**
	 * 使用、绑定
	 */
	public void binding(MerchantDomain merchantDomain) {
		if (merchantDomain != null) {
			this.realMerchant = merchantDomain;
			binding = true;
			virtualMerchant.setStatus(PoolState.USED);// 标记使用
			merchantPool.use(this);
		} else {
			throw new NullPointerException("真实商户不能为空...");
		}
	}

	/**
	 * 失效
	 */
	public void disable() {
		virtualMerchant.setStatus(PoolState.DISABLE);
		merchantPool.disable(this);
	}

}
