package com.spring.course.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupDivideRequest {
	private List<String> divConditions;
	private List<Map<String, Object>> memberList;
	private int divMethod;
	private int divNo;
}
