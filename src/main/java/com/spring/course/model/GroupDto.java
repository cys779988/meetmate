package com.spring.course.model;

import com.spring.security.model.UserEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupDto {
	
	private String memberId;

	private String memberName;
	
	private Long courseId;
	
	private Long divNo;
	
	private String assignmentType;

	private String createdDate;
	
	public GroupEntity toEntity(){
		GroupEntity groupEntity = GroupEntity.builder()
	              .id(GroupID.builder()
	            		  	.course(CourseEntity.builder()
	            		  						.id(courseId)
	            		  						.build())
	            		  	.member(UserEntity.builder()
	            		  						.email(memberId)
	            		  						.build())
	            		  	.build())
	              .divNo(divNo)
	              .build();
	      return groupEntity;
	}
	
	@Builder
	public GroupDto(String memberId, String memberName, Long courseId, Long divNo, String assignmentType, String createdDate) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.courseId = courseId;
		this.divNo = divNo;
		this.assignmentType = assignmentType;
		this.createdDate = createdDate;
	}
}
