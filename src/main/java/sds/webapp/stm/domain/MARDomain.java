package sds.webapp.stm.domain;

import java.util.LinkedList;

import sds.webapp.acc.domain.MerchantDomain;
import sds.webapp.acc.domain.UserDomain;

public class MARDomain {

	private MerchantDomain merchant;
	private LinkedList<UserDomain> agents;

	public MARDomain(MerchantDomain merchantDomain, LinkedList<UserDomain> agents) {

		this.merchant = merchantDomain;
		this.agents = agents;

	}

	public MerchantDomain getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantDomain merchant) {
		this.merchant = merchant;
	}

	public LinkedList<UserDomain> getAgents() {
		return agents;
	}

	public void setAgents(LinkedList<UserDomain> agents) {
		this.agents = agents;
	}

}
