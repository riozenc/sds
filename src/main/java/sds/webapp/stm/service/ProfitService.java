package sds.webapp.stm.service;

import java.util.List;

import sds.common.webapp.base.service.BaseService;
import sds.webapp.stm.domain.ProfitDomain;

public interface ProfitService extends BaseService<ProfitDomain> {

	public int insertBatch(List<ProfitDomain> list);
}
