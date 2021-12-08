package com.spring.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PageVO {
	private Integer page;
	private Integer perPage;

	@Builder
	public PageVO(Integer page, Integer perPage) {
		this.page = page-1;
		this.perPage = perPage;
	}
}
