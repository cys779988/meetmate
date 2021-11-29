package com.spring.common.model;

public class Page {
	private Integer perPage;
	private Integer page;

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void calcPage() {
		page = page > 1 ? (page - 1) * perPage: 0;
	}
}