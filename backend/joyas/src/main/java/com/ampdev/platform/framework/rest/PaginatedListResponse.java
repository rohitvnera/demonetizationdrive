package com.ampdev.platform.framework.rest;

import java.util.List;

import com.ampdev.platform.framework.dataaccess.PaginateBy;

/**
 * @authour Mouli Mukherjee <moulimukherjee@gmail.com>
 */
public class PaginatedListResponse<T> {
	private List<T> items;

	private Integer offset;

	private Integer size;

	private Integer currentCount;

	private Long total;

	private Boolean hasMore;

	public PaginatedListResponse(List<T> items, Long total, Integer currentCount, PaginateBy paginateBy) {
		this.items = items;
		this.total = total;
		this.currentCount = currentCount;
		this.size = paginateBy.getSize() == null ? currentCount : paginateBy.getSize();
		this.offset = paginateBy.getOffset() == null ? 0 : paginateBy.getOffset();
		this.hasMore = (total - this.offset) > currentCount;

	}

	public List<T> getItems() {
		return items;
	}

	public PaginatedListResponse setItems(List<T> items) {
		this.items = items;
		return this;
	}

	public Integer getOffset() {
		return offset;
	}

	public PaginatedListResponse setOffset(Integer offset) {
		this.offset = offset;
		return this;
	}

	public Integer getSize() {
		return size;
	}

	public PaginatedListResponse setSize(Integer size) {
		this.size = size;
		return this;
	}

	public Long getTotal() {
		return total;
	}

	public PaginatedListResponse setTotal(Long total) {
		this.total = total;
		return this;
	}

	public Boolean getHasMore() {
		return hasMore;
	}

	public PaginatedListResponse setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
		return this;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public PaginatedListResponse setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
		return this;
	}
}
