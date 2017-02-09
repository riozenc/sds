package sds.common.pool;

import sds.webapp.acc.domain.MerchantDomain;

public class PoolBean {

	private MerchantPool merchantPool;
	private MerchantDomain merchantDomain;
	private boolean valid;
	private boolean used;
	private long createdTimestamp;
	private long lastUsedTimestamp;
	protected long borrowedTimestamp;

	public PoolBean(MerchantDomain merchantDomain, MerchantPool merchantPool) {
		this.merchantDomain = merchantDomain;
		this.merchantPool = merchantPool;
		this.valid = true;
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

	public boolean isValid() {
		return valid && !used && merchantDomain != null && (merchantDomain.getStatus() == PoolState.NOT_USED
				|| merchantDomain.getStatus() == PoolState.TO_USED);
	}

	public MerchantDomain getObject() {
		return merchantDomain;
	}

	/**
	 * 回收
	 */
	public void recover() {
		merchantPool.recover(this);
	}

	/**
	 * 使用
	 */
	public void used() {
		merchantPool.used(this);
	}

	/**
	 * 失效
	 */
	public void disable() {
		merchantPool.disable(this);
	}

}
