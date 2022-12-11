package com.spring.group;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.course.model.CourseEntity;
import com.spring.course.model.GroupEntity;
import com.spring.course.model.GroupID;
import com.spring.course.repository.CourseRepository;
import com.spring.course.repository.GroupRepository;
import com.spring.group.model.QGroupEntity;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class GroupDataJpaTest {

	@Autowired
	EntityManager em;
	
	JPAQueryFactory queryFactory;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private GroupRepository groupRepository;

	private static Long ID;
	private static String USER_ID = "admin"; 
	@BeforeEach
	public void init() {
		queryFactory = new JPAQueryFactory(em);
		
		UserEntity userEntity = UserEntity.builder()
											.email(USER_ID)
											.password("admin")
											.name("admin")
											.role(Role.ADMIN)
											.build();
		
		userRepository.save(userEntity);
		
		CourseEntity courseEntity = CourseEntity.builder()
												.title("테스트제목")
												.registrant(userEntity)
												.content("테스트내용")
												.category(1L)
												.applyStartDate(LocalDate.now())
												.applyEndDate(LocalDate.now())
												.startDate(LocalDate.now())
												.endDate(LocalDate.now())
												.divclsNo(5)
												.maxNum(20)
												.curNum(0)
												.node(null)
												.edge(null)
												.build();
		
		ID = courseRepository.save(courseEntity).getId();
	}
	
	@Test
	@DisplayName("과정 신청")
	public void apply() {
		CourseEntity courseEntity = courseRepository.findById(ID).get();
		
		UserEntity userEntity = userRepository.findById(USER_ID).get();
		
		GroupID groupId = GroupID.builder()
								.course(courseEntity)
								.member(userEntity)
								.build();
		
		GroupEntity groupEntity = GroupEntity.builder()
											.id(groupId)
											.build();
		GroupID result = groupRepository.save(groupEntity).getId();
		
		assertThat(groupId.equals(result), is(true));
	}
	
	@Test
	@DisplayName("과정그룹의 회원 조회")
	public void getGroupUsers() {
		apply();

		List<GroupEntity> userList = groupRepository.findById_course_id(ID);
		
		assertThat(userList.isEmpty(), is(false));
	}
	
	@Test
	@DisplayName("그룹 분반 초기화")
	public void getCourse() {
		apply();
		
		QGroupEntity groupEntity = QGroupEntity.groupEntity;
		
		queryFactory.update(groupEntity)
					.set(groupEntity.divNo, 0L)
					.where(groupEntity.id.course.id.eq(ID))
					.execute();
		
		boolean result = groupRepository.findById_course_id(ID).stream().allMatch(i->i.getDivNo()==null);
		
		assertThat(result, is(true));
	}
}
