package com.spring.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.List;
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

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.QBoardEntity;
import com.spring.board.model.QBoardFileEntity;
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.ReplyRepository;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BoardDataJpaTest {

	@Autowired
	EntityManager em;
	
	JPAQueryFactory queryFactory;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	private static final String ID = "admin";
	
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
		
		List<BoardEntity> boardEntityList = Arrays.asList(
				BoardEntity.builder()
				.title("테스트제목")
				.content("테스트내용")
				.registrant(userEntity)
				.build(),
				BoardEntity.builder()
				.title("테스트제목")
				.content("테스트내용")
				.registrant(userEntity)
				.build()
		);
		
		boardRepository.saveAll(boardEntityList);
	}
	
	@Test
	@DisplayName("게시글 목록 조회")
	public void getBoards() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "createdDate");
		
    	Page<BoardEntity> pageBoards = boardRepository.findByTitleContaining(pageRequest, "");
		
		assertThat(pageBoards.getContent(), notNullValue());
	}
	
	@Test
	@DisplayName("게시글 조회")
	public void getBoard() {
		Optional<UserEntity> userWrapper = userRepository.findById(ID);
		QBoardEntity boardEntity = QBoardEntity.boardEntity;
		QBoardFileEntity boardFileEntity = QBoardFileEntity.boardFileEntity;
		
		Long id = boardRepository.save(BoardEntity.builder()
										.title("테스트제목")
										.content("테스트내용")
										.registrant(userWrapper.get())
										.build()).getId();
		
		BoardEntity result = queryFactory.selectFrom(boardEntity)
										.leftJoin(boardFileEntity).on(boardEntity.id.eq(boardFileEntity.board.id))
										.fetchJoin()
										.where(boardEntity.id.eq(id))
										.fetchOne();
		
		assertThat(result, notNullValue());
	}
	
	@Test
	@DisplayName("게시글 등록")
	public void addBoard() {
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
	public void deleteBoard() {
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
	public void addReply() {
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
	public void deleteReply() {
		Optional<UserEntity> userWrapper = userRepository.findById(ID);

		Long beforeCount = replyRepository.count();
		
		ReplyDto dto = ReplyDto.builder()
				.registrant("admin")
				.content("테스트내용")
				.build();
		
		ReplyEntity entity = dto.toEntity(userWrapper.get());
		Long id = replyRepository.save(entity).getId();
		
		replyRepository.deleteById(id);
		Long afterCount = replyRepository.count();
		
		assertThat(beforeCount - afterCount, is(0L));
	}
	
	@Test
	@DisplayName("댓글이 있는 게시글 삭제")
	public void deleteBoardAndReply() {
		Optional<UserEntity> userWrapper = userRepository.findById(ID);
		
		BoardEntity boardEntity = BoardEntity.builder()
												.title("테스트제목")
												.content("테스트내용")
												.registrant(userWrapper.get())
												.build();
		
		Long boardId = boardRepository.saveAndFlush(boardEntity).getId();
		
		ReplyEntity replyEntity = ReplyEntity.builder()
												.registrant(userWrapper.get())
												.content("테스트댓글")
												.board(boardEntity)
												.build();
		
		Long replyId = replyRepository.saveAndFlush(replyEntity).getId();
		
		em.clear();
		
		boardRepository.deleteById(boardId);
		
		em.flush();
		em.clear();
		
		Optional<ReplyEntity> replyWrapper = replyRepository.findById(replyId);
		assertThat(replyWrapper.isPresent(), is(false));
	}

}
