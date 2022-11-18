package com.spring.course.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.model.UserEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseDto {
	private Long id;
	
	private String registrant;
	
	@NotBlank(message = "코스명은 필수값입니다.")
	private String title;
	
	@NotBlank(message = "코스내용은 필수값입니다.")
	private String content;
	
	private Long category;
	
	private String categoryName;
	
	private Integer divclsNo;

	@NotNull(message = "최대인원은 필수값입니다.")
	private Integer maxNum;
	
	private Integer curNum;

	private List<Object> node;

	private List<Object> edge;
	
    private LocalDateTime createdDate;
    
	public CourseEntity toEntity() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		CourseEntity courseEntity = CourseEntity.builder()
	              .id(id)
	              .registrant(UserEntity.builder().email(registrant).build())
	              .title(title)
	              .content(content)
	              .category(category)
	              .divclsNo(divclsNo)
	              .maxNum(maxNum)
	              .curNum(curNum)
	              .node(objectMapper.writeValueAsString(node))
	              .edge(objectMapper.writeValueAsString(edge))
	              .build();
	      return courseEntity;
	}
	
	@Builder
	public CourseDto(Long id, String registrant, String title, String content, String categoryName, Long category, Integer divclsNo, Integer maxNum, Integer curNum, LocalDateTime createdDate, List<Object> node, List<Object> edge) {
		this.id = id;
		this.registrant = registrant;
		this.title = title;
		this.content = content;
		this.category = category;
		this.categoryName = categoryName;
		this.divclsNo = divclsNo;
		this.maxNum = maxNum;
		this.curNum = curNum;
		this.node = node;
		this.edge = edge;
		this.createdDate = createdDate;
	}
}
