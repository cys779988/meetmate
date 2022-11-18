package com.spring.code.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CodeRequest {
	private List<CommonCodeEntity> rows = new ArrayList<>();
}
