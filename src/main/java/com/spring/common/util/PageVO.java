package com.spring.common.util;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PageVO {
	private Integer page;
	private Integer perPage;

	public void calcPage() {
		page = page > 1 ? (page - 1) * perPage: 0;
	}
	
	@Builder
	public PageVO(Integer page, Integer perPage) {
		this.page = page;
		this.perPage = perPage;
	}
}
