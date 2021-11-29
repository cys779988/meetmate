package com.spring.course;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.course.model.CategoryEntity;
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

	/*
	 * @BeforeEach public void init() { queryFactory = new JPAQueryFactory(em); }
	 */

	@Test
	public void querydsl_test() {

		String title = "테스트제목";
		String content = "테스트내용";
		Integer divclsNo = 5;
		Integer maxNum = 20;
		Integer curNum = 0;
		courseRepository.save(CourseEntity.builder()
				.title(title)
				.content(content)
				.category(CategoryEntity.builder().id(1L).build())
				.divclsNo(divclsNo)
				.maxNum(maxNum)
				.curNum(curNum)
                .build());

		Long count = courseRepositorySupport.findCountByTitle(title);
		assertThat(count, not(0L));
	}

	@Test
	@DisplayName("Course 등록 후 조회")
	public void addCourse() throws JsonProcessingException {
		CourseDto dto = CourseDto.builder()
						.title("테스트제목")
						.content("테스트내용")
						.category(1L)
						.divclsNo(5)
						.maxNum(20)
						.curNum(0)
						.build();

		CourseEntity entity = dto.toEntity();
		Long id = courseRepository.save(entity).getId();
		
		assertThat(courseRepository.findById(id).get().getId(), is(id));
	}
	
	@Test
	@DisplayName("Course 삭제")
	public void deleteCourse() throws JsonProcessingException {
		Long beforeCount = courseRepository.count();
		
		CourseDto dto = CourseDto.builder()
				.title("테스트제목")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.build();
		
		CourseEntity entity = dto.toEntity();
		Long id = courseRepository.save(entity).getId();

		courseRepository.deleteById(id);
		
		Long afterCount = courseRepository.count();
		assertThat(beforeCount - afterCount, is(0L));
	}

}
