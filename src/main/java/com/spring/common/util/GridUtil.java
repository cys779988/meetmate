package com.spring.common.util;

import java.util.List;

import com.spring.common.model.GridForm;
import com.spring.common.model.GridResponse;

public class GridUtil {
	private GridUtil() {}
	
	public static GridForm of(int pageNum, long totalCount, List<?> contentList) {
		GridResponse gridResponse = GridResponse.builder()
									.page(pageNum)
									.totalCount(totalCount)
									.contentList(contentList)
									.build();
		
		return new GridForm(true, gridResponse);
	}
}
