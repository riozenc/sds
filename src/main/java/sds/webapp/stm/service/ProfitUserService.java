package sds.webapp.stm.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.stm.domain.ProfitUserDomain;

public interface ProfitUserService extends BaseService<ProfitUserDomain> {
	public int insertBatch(List<ProfitUserDomain> list);
}
