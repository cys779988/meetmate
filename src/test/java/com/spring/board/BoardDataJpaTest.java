package com.spring.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.ReplyRepository;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Transactional
public class BoardDataJpaTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private BoardRepositorySupport boardRepositorySupport;

	private static final String ID = "admin";
	
	@BeforeAll
	public void createUser() {
		userRepository.save(UserEntity.builder().email(ID).password("admin").name("admin").role(Role.GUEST).build());
	}
	
	@Test
	@DisplayName("게시글 제목으로 카운트 조회")
	public void countByTitleBoard() {

		String title = "테스트제목77998877777777777";
		
		boardRepository.save(BoardEntity.builder()
								.title(title)
								.registrant(userRepository.getOne(ID))
								.content("테스트내용")
								.build());
		
		Long result = boardRepositorySupport.findCountByTitle(title);
		
		assertThat(result, is(1L));
	}

	@Test
	@DisplayName("게시글 등록")
	public void addBoard() throws JsonProcessingException {
		Optional<UserEntity> userWrapper = userRepository.findById(ID);
				
		BoardEntity entity = BoardEntity.builder()
										.title("테스트제목")
										.content("테스트내용")
										.registrant(userWrapper.get())
										.build();
		
		Long id = boardRepository.save(entity).getId();
		
		Optional<BoardEntity> boardWrapper = boardRepository.findById(id);
		
		assertThat(boardWrapper.get(), notNullValue());
	}

	@Test
	@DisplayName("게시글 삭제")
	public void deleteBoard() throws JsonProcessingException {
		Long beforeCount = boardRepository.count();
		
		Optional<UserEntity> userWrapper = userRepository.findById(ID);
		
		BoardEntity entity = BoardEntity.builder()
				.title("테스트제목")
				.content("테스트내용")
				.registrant(userWrapper.get())
				.build();
		
		Long id = boardRepository.save(entity).getId();

		boardRepository.deleteById(id);
		Long afterCount = boardRepository.count();
		
		assertThat(beforeCount - afterCount, is(0L));
	}
	
	@Test
	@DisplayName("댓글 등록")
	public void addReply() throws JsonProcessingException {
		ReplyDto dto = ReplyDto.builder()
				.registrant("admin")
				.content("테스트내용")
				.build();
		Optional<UserEntity> userWrapper = userRepository.findById(ID);
		
		ReplyEntity entity = dto.toEntity(userWrapper.get());
		
		Long id = replyRepository.save(entity).getId();
		
		Optional<ReplyEntity> replyWrapper = replyRepository.findById(id);
		
		assertThat(replyWrapper.get(), notNullValue());
	}
	
	@Test
	@DisplayName("댓글 삭제")
	public void deleteReply() throws JsonProcessingException {
		Long beforeCount = replyRepository.count();
		
		ReplyDto dto = ReplyDto.builder()
				.registrant("admin")
				.content("테스트내용")
				.build();
		Optional<UserEntity> userWrapper = userRepository.findById(ID);
		
		ReplyEntity entity = dto.toEntity(userWrapper.get());
		Long id = replyRepository.save(entity).getId();
		
		replyRepository.deleteById(id);
		Long afterCount = replyRepository.count();
		
		assertThat(beforeCount - afterCount, is(0L));
	}

}
