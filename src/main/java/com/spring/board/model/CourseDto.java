package com.spring.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseDto {
	private Long id;
	private String courseDiv;
	private String name;
	private String type;
	private int playTime;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private Integer rowKey;

	public CourseEntity toEntity(){
		CourseEntity courseEntity = CourseEntity.builder()
	              .id(id)
	              .courseDiv(courseDiv)
	              .name(name)
	              .type(type)
	              .playTime(playTime)
	              .col1(col1)
	              .col2(col2)
	              .col3(col3)
	              .col4(col4)
	              .col5(col5)
	              .build();
	      return courseEntity;
	}
	
	@Builder
	public CourseDto(Long id, String courseDiv, String name, String type, int playTime, String col1, String col2, String col3,
			String col4, String col5, Integer rowKey) {
		this.id = id;
		this.courseDiv = courseDiv;
		this.name = name;
		this.type = type;
		this.playTime = playTime;
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.col4 = col4;
		this.col5 = col5;
		this.rowKey = rowKey;
	}
}
