package com.spring.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.ReplyRepository;
import com.spring.security.model.UserEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BoardDataJpaTest {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private BoardRepositorySupport boardRepositorySupport;

	@Test
	@DisplayName("게시글 제목으로 카운트 조회")
	public void countByTitleBoard() {

		String title = "테스트제목77998877777777777";
		
		boardRepository.save(BoardEntity.builder()
								.title(title)
								.registrant(UserEntity.builder().email("admin").build())
								.content("테스트내용")
								.build());

		Long result = boardRepositorySupport.findCountByTitle(title);
		
		assertThat(result, is(1L));
	}

	@Test
	@DisplayName("게시글 등록")
	public void addBoard() throws JsonProcessingException {
		BoardDto dto = BoardDto.builder()
				.content("테스트내용")
				.registrant("admin")
				.title("테스트제목")
				.build();

		BoardEntity entity = dto.toEntity();
		Long id = boardRepository.save(entity).getId();
		
		assertThat(boardRepository.findById(id).get().getId(), is(id));
	}

	@Test
	@DisplayName("게시글 삭제")
	public void deleteBoard() throws JsonProcessingException {
		Long beforeCount = boardRepository.count();
		
		BoardDto dto = BoardDto.builder()
				.content("테스트내용")
				.registrant("admin")
				.title("테스트제목")
				.build();
		
		BoardEntity entity = dto.toEntity();
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
		
		ReplyEntity entity = dto.toEntity();
		Long id = replyRepository.save(entity).getId();
		
		assertThat(replyRepository.findById(id).get().getId(), is(id));
	}
	
	@Test
	@DisplayName("댓글 삭제")
	public void deleteReply() throws JsonProcessingException {
		Long beforeCount = replyRepository.count();
		
		ReplyDto dto = ReplyDto.builder()
				.registrant("admin")
				.content("테스트내용")
				.build();
		
		ReplyEntity entity = dto.toEntity();
		Long id = replyRepository.save(entity).getId();
		
		replyRepository.deleteById(id);
		Long afterCount = replyRepository.count();
		
		assertThat(beforeCount - afterCount, is(0L));
	}

}
