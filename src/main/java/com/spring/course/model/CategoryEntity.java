package com.spring.course.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tb_category")
public class CategoryEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="CATEGORY_ID")
	private Long id;
	
	private String name;
	
	@Builder
	public CategoryEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
