package com.spring.course.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.code.model.CodeType;
import com.spring.code.service.CommonCodeService;
import com.spring.common.model.GridForm;
import com.spring.common.util.GridUtil;
import com.spring.course.model.CourseDto;
import com.spring.course.model.CourseEntity;
import com.spring.course.repository.CourseRepository;

import javax.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseService {
	private CommonCodeService commonCodeService;
    private CourseRepository courseRepository;
    private ObjectMapper objectMapper;
    
    @Transactional
    public GridForm getCoursesGridForm(Pageable page, Long category) {
    	Page<CourseEntity> pageCourses;
    	
    	if(Objects.isNull(category)) {
    		pageCourses = courseRepository.findAll(PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort()));
    	} else {
    		pageCourses = courseRepository.findByCategory(PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort()), category);
    	}

    	List<CourseDto> contentList = new ArrayList<>();
    	
    	if(pageCourses.hasContent()) {
    		contentList = pageCourses.getContent().stream().map(i -> convertEntityToDto(i)).collect(Collectors.toList());
    	}
    	
        return GridUtil.of(page.getPageNumber(), pageCourses.getTotalElements(), contentList);
    }
    
	public List<CourseDto> getCourseByUser(String email) {
		List<CourseEntity> courseEntityList = courseRepository.findByRegistrant_email(email);
		
		return courseEntityList.stream().map(courseEntity -> convertEntityToDto(courseEntity)).collect(Collectors.toList());
	}
	
    @Transactional
    public CourseDto getCourse(Long id) {
    	Optional<CourseEntity> entityWrapper = courseRepository.findById(id);
    	CourseDto courseDto = new CourseDto();
        if(entityWrapper.isPresent()) {
        	CourseEntity courseEntity = entityWrapper.get();
        	courseDto = convertEntityToDto(courseEntity);
        }
        return courseDto;
    }

    
    @Transactional
    public Long addCourse(CourseDto courseDto) {
    	CourseEntity entity;
    	Long id = null;
		try {
			entity = courseDto.toEntity();
			id = courseRepository.save(entity).getId();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return id;
    }

    @Transactional
    public void deleteCourse(Long id) {
    	courseRepository.deleteById(id);
    }
	
    private CourseDto convertEntityToDto(CourseEntity courseEntity) {
    	Map<String, String> codes = commonCodeService.getCodes(CodeType.COURSE_CATEGORY);
        return CourseDto.builder()
        		.id(courseEntity.getId())
        		.registrant(courseEntity.getRegistrant().getEmail())
        		.title(courseEntity.getTitle())
                .content(courseEntity.getContent())
                .divclsNo(courseEntity.getDivclsNo())
                .category(courseEntity.getCategory())
                .categoryName(codes.get(courseEntity.getCategory().toString()))
                .applyStartDate(courseEntity.getApplyStartDate())
                .applyEndDate(courseEntity.getApplyEndDate())
                .startDate(courseEntity.getStartDate())
                .endDate(courseEntity.getEndDate())
                .maxNum(courseEntity.getMaxNum())
                .curNum(courseEntity.getCurNum())
                .schedules(jsonToList(courseEntity.getSchedules()))
                .node(jsonToList(courseEntity.getNode()))
                .edge(jsonToList(courseEntity.getEdge()))
                .createdDate(courseEntity.getCreatedDate())
                .build();
    }

    private List<Object> jsonToList(String data) {
    	List<Object> readValue = new ArrayList<Object>();
    	if(Objects.nonNull(data)) {
	    	try {
	    		readValue = objectMapper.readValue(data, new TypeReference<List<Object>>() {
				});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		return readValue;
    }

}
