package sds.common.json;

import java.util.List;

public class JsonGrid {
	private Integer count;
	private Integer psize;
	private Object list;

	public JsonGrid(Integer count, Integer psize, Object list) {
		this.count = count;
		this.psize = psize;
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPsize() {
		return psize;
	}

	public void setPsize(Integer psize) {
		this.psize = psize;
	}

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}

}
