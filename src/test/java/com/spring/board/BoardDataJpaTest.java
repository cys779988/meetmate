package com.spring.board;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.board.model.BoardDto;
import com.spring.board.model.BoardEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.security.model.UserEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BoardDataJpaTest {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardRepositorySupport boardRepositorySupport;

	/*
	 * @BeforeEach public void init() { queryFactory = new JPAQueryFactory(em); }
	 */

	@Test
	@Disabled
	public void querydsl_test() {

		String writer = "cys@test.com";
		String title = "테스트제목";
		String content = "테스트내용";
		boardRepository.save(BoardEntity.builder()
								.title(title)
								.registrant(UserEntity.builder().email(writer).build())
								.content(content)
								.build());

		Long result = boardRepositorySupport.findCountByTitle(title);
		assertThat(result, is(Long.parseLong("4")));
	}

	@Test
	@DisplayName("Board 등록 후 조회")
	public void addBoard() throws JsonProcessingException {
		BoardDto dto = BoardDto.builder()
				.content("테스트내용")
				.registrant("cys@test.com")
				.title("테스트제목")
				.build();

		BoardEntity entity = dto.toEntity();
		Long id = boardRepository.save(entity).getId();
		
		assertThat(boardRepository.findById(id).get().getId(), is(id));
	}

	@Test
	@DisplayName("Board 삭제")
	public void deleteBoard() throws JsonProcessingException {
		Long beforeCount = boardRepository.count();
		
		BoardDto dto = BoardDto.builder()
				.content("테스트내용")
				.registrant("cys@test.com")
				.title("테스트제목")
				.build();
		
		BoardEntity entity = dto.toEntity();
		Long id = boardRepository.save(entity).getId();

		boardRepository.deleteById(id);
		
		Long afterCount = boardRepository.count();
		assertThat(beforeCount - afterCount, is(0L));
	}

}
