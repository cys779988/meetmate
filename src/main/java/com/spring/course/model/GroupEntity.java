package com.spring.course.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.spring.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tb_group")
public class GroupEntity extends BaseTimeEntity{
	
	@EmbeddedId
	private GroupID id;
	
	@MapsId("course")
	@ManyToOne
	@JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_group_course"))
	private CourseEntity course;
	
	private Long divNo;
	
	@Builder
	public GroupEntity(GroupID id, Long divNo) {
		this.id = id;
		this.divNo = divNo;
	}
}
