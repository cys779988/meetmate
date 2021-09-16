package com.spring.board.model;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "course")
public class CourseEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@Column(length = 10, nullable = false)
	private String courseDiv;

	@Column(length = 10, nullable = false)
	private String name;

	@Column(length = 10, nullable = false)
	private String type;

	@Column(length = 10)
	private int playTime;

	@Column(length = 10)
	private String col1;

	@Column(length = 10)
	private String col2;

	@Column(length = 10)
	private String col3;

	@Column(length = 10)
	private String col4;

	@Column(length = 10)
	private String col5;

	@Builder
    public CourseEntity(Long id, String courseDiv, String name, String type, int playTime, String col1, String col2, 
    		String col3, String col4, String col5) {
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
    }
}
