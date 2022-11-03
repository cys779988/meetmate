package com.spring.common.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GridResponse {
	private GridPagination pagination;
	
	private List<?> contents;
	
	@Getter
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	class GridPagination {
		private int page;
		
		private long totalCount;
	}
	
	@Builder
	public GridResponse(int page, long totalCount, List<?> contentList) {
		this.pagination = new GridPagination(page+1, totalCount);
		this.contents = contentList;
	}
}
