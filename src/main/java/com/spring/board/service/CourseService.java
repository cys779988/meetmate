package com.spring.board.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.spring.board.model.CourseDto;
import com.spring.board.model.CourseEntity;
import com.spring.board.repository.CourseRepository;
import com.spring.board.repository.CourseRepositorySupport;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CourseService {
    private CourseRepository courseRepository;
    private CourseRepositorySupport courseRepositorySupport;
    @Transactional
    public List<CourseDto> getCourseList(Integer pageNum, Integer perPage) {
    	Page<CourseEntity> page = courseRepository.findAll(PageRequest.of(pageNum-1, perPage, Sort.by(Direction.ASC, "id")));
        List<CourseEntity> courseEntities = page.getContent();
        List<CourseDto> courseDtoList = new ArrayList<>();
        int rowKey = 0;

        for ( CourseEntity courseEntity : courseEntities) {
        	courseDtoList.add(this.convertEntityToDto(courseEntity, rowKey++));
        }

        return courseDtoList;
    }
    
    @Transactional
    public CourseDto getPost(Long id) {
        Optional<CourseEntity> entityWrapper = courseRepository.findById(id);
        CourseEntity courseEntity = entityWrapper.get();
        CourseDto courseDto = CourseDto.builder()
                .courseDiv(courseEntity.getCourseDiv())
                .name(courseEntity.getName())
                .type(courseEntity.getType())
                .playTime(courseEntity.getPlayTime())
                .col1(courseEntity.getCol1())
                .col2(courseEntity.getCol2())
                .col3(courseEntity.getCol3())
                .col4(courseEntity.getCol4())
                .col5(courseEntity.getCol5())
                .build();
       //BeanUtils.copyProperties(courseEntity, courseDto);

        return courseDto;
    }

    @Transactional
    public void add(List<CourseDto> courseDto) {
    	courseDto.stream().forEach(dto -> {
    		courseRepository.save(dto.toEntity()).getId();
    	});
    }

    @Transactional
    public void delete(List<CourseDto> param) {
    	Iterator<CourseDto> iterator =  param.iterator();
    	
    	while (iterator.hasNext()) {
			CourseDto courseDto = (CourseDto) iterator.next();
			courseRepository.deleteById(courseDto.getId());
		}
    }
    
    @Transactional
    public Long getCourseCount() {
        return courseRepository.count();
    }
    
    private CourseDto convertEntityToDto(CourseEntity courseEntity, int rowKey) {
        return CourseDto.builder()
        		.id(courseEntity.getId())
                .courseDiv(courseEntity.getCourseDiv())
                .name(courseEntity.getName())
                .type(courseEntity.getType())
                .playTime(courseEntity.getPlayTime())
                .col1(courseEntity.getCol1())
                .col2(courseEntity.getCol2())
                .col3(courseEntity.getCol3())
                .col4(courseEntity.getCol4())
                .col5(courseEntity.getCol5())
                .rowKey(rowKey)
                .build();
    }

}
