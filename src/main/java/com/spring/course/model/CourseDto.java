package com.spring.course.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	@NotBlank(message = "제목은 필수값입니다.")
	private String title;
	
	private String content;
	
	private Long category;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "신청 시작일자는 필수값입니다.")
	private LocalDate applyStartDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "신청 종료일자는 필수값입니다.")
	private LocalDate applyEndDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "시작일자는 필수값입니다.")
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "종료일자는 필수값입니다.")
	private LocalDate endDate;
	
	private String categoryName;
	
	private Integer divclsNo;

	@NotNull(message = "최대인원은 필수값입니다.")
	private Integer maxNum;
	
	private Integer curNum;

	private List<Object> schedules;
	
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
	              .applyStartDate(applyStartDate)
	              .applyEndDate(applyEndDate)
	              .startDate(startDate)
	              .endDate(endDate)
	              .divclsNo(divclsNo)
	              .maxNum(maxNum)
	              .curNum(curNum)
	              .schedules(objectMapper.writeValueAsString(schedules))
	              .node(objectMapper.writeValueAsString(node))
	              .edge(objectMapper.writeValueAsString(edge))
	              .build();
	      return courseEntity;
	}
	
	@Builder
	public CourseDto(Long id, String registrant, String title, String content, String categoryName, Long category, LocalDate applyStartDate, LocalDate applyEndDate, LocalDate startDate, LocalDate endDate, Integer divclsNo, Integer maxNum, Integer curNum, LocalDateTime createdDate, List<Object> schedules, List<Object> node, List<Object> edge) {
		this.id = id;
		this.registrant = registrant;
		this.title = title;
		this.content = content;
		this.category = category;
		this.categoryName = categoryName;
		this.applyStartDate = applyStartDate;
		this.applyEndDate = applyEndDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.divclsNo = divclsNo;
		this.maxNum = maxNum;
		this.curNum = curNum;
		this.schedules = schedules;
		this.node = node;
		this.edge = edge;
		this.createdDate = createdDate;
	}
}
