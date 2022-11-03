package com.spring.course;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.course.model.CourseDto;
import com.spring.course.model.CourseEntity;
import com.spring.course.repository.CourseRepository;
import com.spring.course.repository.CourseRepositorySupport;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CourseDataJpaTest {

	@Autowired
	private CourseRepository courseRepository;


	@Autowired
	private CourseRepositorySupport courseRepositorySupport;
	
	
    private CourseDto getCourseData() {
    	return CourseDto.builder()
				.title("테스트제목")
				.registrant("admin")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(null)
				.edge(null)
				.build();
    }
    
	@Test
	@DisplayName("과정 제목으로 카운트 조회")
	public void countByTitleBoard() throws JsonProcessingException {
    	
		String title = "테스트제목77998877777777777";
		
		CourseDto dto = CourseDto.builder()
				.title(title)
				.registrant("admin")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(null)
				.edge(null)
				.build();

		CourseEntity entity = dto.toEntity();
		courseRepository.save(entity);
		Long result = courseRepositorySupport.findCountByTitle(title);
		
		assertThat(result, is(1L));
	}
	
	@Test
	@DisplayName("과정 수정")
	public void updateCourse() throws JsonProcessingException {
		
		String title = "테스트제목77998877777777777";
		CourseDto dto = CourseDto.builder()
				.title(title)
				.registrant("admin")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.build();
		
		CourseEntity entity = dto.toEntity();
		CourseEntity saveEntity = courseRepository.save(entity);
		
		Long saveId = saveEntity.getId();
		saveEntity.setContent("수정된내용");
		saveEntity.setMaxNum(30);
		courseRepositorySupport.updateCourse(saveEntity);
		CourseEntity updateEntity = courseRepository.findById(saveId).get();
		
		assertThat(dto.getContent(), not(updateEntity.getContent()));
	}

	@Test
	@DisplayName("과정 등록")
	public void addBoard() throws JsonProcessingException {
		CourseDto dto = getCourseData();
		
		CourseEntity entity = dto.toEntity();
		Long id = courseRepository.save(entity).getId();
		
		assertThat(courseRepository.findById(id).get().getId(), is(id));
	}

	@Test
	@DisplayName("과정 삭제")
	public void deleteBoard() throws JsonProcessingException {
		Long beforeCount = courseRepository.count();
		
		CourseDto dto = getCourseData();
		
		CourseEntity entity = dto.toEntity();
		Long id = courseRepository.save(entity).getId();

		courseRepository.deleteById(id);
		Long afterCount = courseRepository.count();
		
		assertThat(beforeCount - afterCount, is(0L));
	}

}
