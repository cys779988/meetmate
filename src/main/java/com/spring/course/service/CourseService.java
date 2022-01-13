package com.spring.course.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.model.PageVO;
import com.spring.common.util.AppUtil;
import com.spring.course.model.CategoryEntity;
import com.spring.course.model.CourseDto;
import com.spring.course.model.CourseEntity;
import com.spring.course.repository.CategoryRepository;
import com.spring.course.repository.CourseRepository;
import com.spring.course.repository.CourseRepositorySupport;

import javax.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseService {
    private CourseRepository courseRepository;
    private CategoryRepository categoryRepository;
    private ObjectMapper objectMapper;
    private CourseRepositorySupport courseRepositorySupport;
    
    @Transactional
    public List<CourseDto> getCourses(PageVO pageVO, Long id) {
    	Page<CourseEntity> page;
    	if(id == null) {
    		page = courseRepository.findAll(PageRequest.of(pageVO.getPage(), pageVO.getPerPage(), Sort.by(Direction.ASC, "createdDate")));
    	} else {
    		page = courseRepository.findByCategoryId(PageRequest.of(pageVO.getPage(), pageVO.getPerPage(), Sort.by(Direction.ASC, "createdDate")), id);
    	}
        List<CourseEntity> courseEntities = page.getContent();
        List<CourseDto> courseDtoList = new ArrayList<>();

        for ( CourseEntity courseEntity : courseEntities) {
        	courseDtoList.add(this.convertEntityToDto(courseEntity));
        }

        return courseDtoList;
    }
    
    @Transactional
    public Long getCourseCount(Long id) {
    	if(id == null) {
    		return courseRepository.count();
    	}
        return courseRepository.countByCategoryId(id);
    }

	public List<CategoryEntity> getCategories() {
		return categoryRepository.findAll();
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
    public Long updateCourse(CourseDto courseDto) {
    	CourseEntity entity;
    	Long id = null;
    	try {
    		entity = courseDto.toEntity();
    		courseRepositorySupport.updateCourse(entity);
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
        return CourseDto.builder()
        		.id(courseEntity.getId())
        		.registrant(courseEntity.getRegistrant().getEmail())
        		.title(courseEntity.getTitle())
                .content(courseEntity.getContent())
                .divclsNo(courseEntity.getDivclsNo())
                .category(courseEntity.getCategory().getId())
                .categoryName(courseEntity.getCategory().getName())
                .maxNum(courseEntity.getMaxNum())
                .curNum(courseEntity.getCurNum())
                .node(jsonToList(courseEntity.getNode()))
                .edge(jsonToList(courseEntity.getEdge()))
                .createdDate(courseEntity.getCreatedDate())
                .build();
    }

    private List<Object> jsonToList(String data) {
    	List<Object> readValue = new ArrayList<Object>();
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
		return readValue;
    }

}
