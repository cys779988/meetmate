package com.spring.board;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.board.model.CourseDto;
import com.spring.board.model.CourseEntity;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.CourseRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BoardDataJpaTest {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private BoardRepositorySupport boardRepositorySupport;
	
	/*
	 * @AfterAll public void tearDown(){ boardRepository.deleteAllInBatch(); }
	 */
	/*
	 * @BeforeEach public void init() { queryFactory = new JPAQueryFactory(em); }
	 */
	
	@Test
	@Disabled
	public void querydsl_test() {
		/*
		 * String writer = "ys"; String title = "test0"; String content = "content0";
		 * boardRepository.save(BoardEntity.builder().title(title).writer(writer).
		 * content(content).build());
		 */
		String title = "test0";
		
		Long result = boardRepositorySupport.findCountByTitle(title);
		assertThat(result, is(Long.parseLong("4")));
	}
	
	@Test
	public void addTest() {
		List<CourseDto> contentsList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			CourseDto dto =  CourseDto.builder()
					.courseDiv("집합")
					.name("테스트"+i)
					.type("Y")
					.playTime(12)
					.col1("기본역량")
					.col2("분임활동")
					.col3("집합교육")
					.col4("0")
					.build();
			contentsList.add(dto);
			
			CourseEntity entity = dto.toEntity();
			courseRepository.save(entity);
		}
	}
	
	@Test
	@Disabled
	public void getCount() {
		Long count = courseRepository.count();
		assertThat(count, is(8L));
	}
	
	@Test
	@Disabled
	public void getTest() {
		List<CourseEntity> entity = courseRepository.findAll();
		assertThat(entity.size(), is(6));
	}
}
