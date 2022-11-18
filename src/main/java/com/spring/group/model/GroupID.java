package com.spring.group.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.spring.course.model.CourseEntity;
import com.spring.security.model.UserEntity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Embeddable
public class GroupID implements Serializable {
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_group_course"))
	private CourseEntity course;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id", referencedColumnName = "email", foreignKey = @ForeignKey(name = "fk_group_member"))
	private UserEntity member;
	
	@Builder
	public GroupID(CourseEntity course, UserEntity member) {
		this.course = course;
		this.member = member;
	}
	
}
