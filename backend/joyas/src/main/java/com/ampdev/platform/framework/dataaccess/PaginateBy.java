package com.ampdev.platform.framework.dataaccess;

/**
 * @authour Mouli Mukherjee <moulimukherjee@gmail.com>
 */
public class PaginateBy {

	public static final int MAX_FETCH_SIZE = 100;

	private Integer offset;

	private Integer size;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getSize() {
		return size;
	}

	public PaginateBy setSize(Integer size) {
		this.size = size;
		return this;
	}
}
