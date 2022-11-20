package com.spring.course;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.course.model.CourseDto;
import com.spring.course.model.CourseEntity;
import com.spring.course.model.QCourseEntity;
import com.spring.course.repository.CourseRepository;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseDataJpaTest {

	@Autowired
	EntityManager em;
	
	JPAQueryFactory queryFactory;
	
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	private static long ID;

	@BeforeEach
	public void init() {
		queryFactory = new JPAQueryFactory(em);
		
		UserEntity userEntity = UserEntity.builder()
											.email("admin")
											.password("admin")
											.name("admin")
											.role(Role.ADMIN)
											.build();
		
		userRepository.save(userEntity);
		
		CourseEntity courseEntity = CourseEntity.builder()
												.id(ID)
												.title("테스트제목")
												.registrant(userEntity)
												.content("테스트내용")
												.category(1L)
												.divclsNo(5)
												.maxNum(20)
												.curNum(0)
												.node(null)
												.edge(null)
												.build();
		
		ID = courseRepository.save(courseEntity).getId();
	}
	
	@Test
	@DisplayName("과정 목록 조회")
	public void getCourses() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "createdDate");
		
		Page<CourseEntity> pageCourses = courseRepository.findAll(pageRequest);
		
		assertThat(pageCourses.getContent(), notNullValue());
	}
	
	@Test
	@DisplayName("과정 목록 카테고리로 조회")
	public void getCoursesByCategory() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "createdDate");
		
		Page<CourseEntity> pageCourses = courseRepository.findByCategory(pageRequest, 1L);
		
		assertThat(pageCourses.getContent(), notNullValue());
	}
	
	@Test
	@DisplayName("과정 조회")
	public void getCourse() {
		Optional<CourseEntity> entityWrapper = courseRepository.findById(ID);
		
		assertThat(entityWrapper.isPresent(), is(true));
	}
	
	@Test
	@DisplayName("과정 등록")
	public void addCourse() throws JsonProcessingException {
		CourseDto dto = CourseDto.builder()
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
		
		CourseEntity entity = dto.toEntity();
		Long id = courseRepository.save(entity).getId();
		
		assertThat(courseRepository.findById(id).get().getId(), is(id));
	}

	@Test
	@DisplayName("과정 삭제")
	public void deleteCourse() {
		Long beforeCount = courseRepository.count();
		
		courseRepository.deleteById(ID);
		
		Long afterCount = courseRepository.count();
		
		assertThat(beforeCount - afterCount, is(1L));
	}
	
	@Test
	@DisplayName("과정 인원 수 증가")
	public void update() {
		QCourseEntity courseEntity =  QCourseEntity.courseEntity;
		
		Long affectedRow = queryFactory.update(courseEntity)
						.set(courseEntity.curNum, courseEntity.curNum.add(1))
						.where(courseEntity.id.eq(ID))
						.execute();
		
		assertThat(affectedRow, is(1L));
	}

}
