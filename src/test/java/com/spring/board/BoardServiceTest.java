package com.spring.board;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.spring.board.model.BoardDto;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.BoardRequest;
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.ReplyRepository;
import com.spring.board.service.BoardService;
import com.spring.common.model.GridForm;
import com.spring.common.model.GridResponse;
import com.spring.common.util.AppUtil;
import com.spring.common.util.GridUtil;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@InjectMocks
	private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;	

    @Mock
    private UserRepository userRepository;	
	
    @Mock
    private BoardRepositorySupport boardRepositorySupport;	
    
    @Mock
    private ReplyRepository replyRepository;	
    
    private static final String ID = "admin";
    private BoardEntity getBoardData() {
    	UserEntity user =  UserEntity.builder()
				.email(ID)
				.name("채영수")
				.role(Role.GUEST)
				.password("1234")
				.build();
    	
    	BoardEntity board = BoardEntity.builder()
    			.id(1L)
    			.registrant(user)
    			.title("테스트제목")
    			.content("테스트글")
    			.build();
    	return board;
    }
    
    private List<ReplyEntity> getReplyData() {
    	BoardEntity board = getBoardData();
    	
    	List<ReplyEntity> replyList = Arrays.asList(
    		ReplyEntity.builder().id(1L).registrant(board.getRegistrant()).board(board).content("테스트댓글").build(),
    		ReplyEntity.builder().id(2L).registrant(board.getRegistrant()).board(board).content("테스트댓글").build(),
    		ReplyEntity.builder().id(3L).registrant(board.getRegistrant()).board(board).content("테스트댓글").build(),
    		ReplyEntity.builder().id(4L).registrant(board.getRegistrant()).board(board).content("테스트댓글").build(),
    		ReplyEntity.builder().id(5L).registrant(board.getRegistrant()).board(board).content("테스트댓글").build()
    	);
    	
    	return replyList;
    }
    
    private List<BoardEntity> getBoardListData() {
    	UserEntity user =  UserEntity.builder()
    							.email(ID)
    							.name("채영수")
    							.role(Role.GUEST)
    							.password("")
    							.build();
    	
    	List<BoardEntity> boardList = Arrays.asList(
    			BoardEntity.builder().id(1L).registrant(user).title("테스트제목1").content("테스트글1").build(),
		    	BoardEntity.builder().id(2L).registrant(user).title("테스트제목2").content("테스트글2").build(),
		    	BoardEntity.builder().id(3L).registrant(user).title("테스트제목3").content("테스트글3").build(),
		    	BoardEntity.builder().id(4L).registrant(user).title("테스트제목4").content("테스트글4").build(),
		    	BoardEntity.builder().id(5L).registrant(user).title("테스트제목5").content("테스트글5").build(),
		    	BoardEntity.builder().id(6L).registrant(user).title("테스트제목6").content("테스트글6").build(),
		    	BoardEntity.builder().id(7L).registrant(user).title("테스트제목7").content("테스트글7").build(),
		    	BoardEntity.builder().id(8L).registrant(user).title("테스트제목8").content("테스트글8").build(),
		    	BoardEntity.builder().id(9L).registrant(user).title("테스트제목9").content("테스트글9").build(),
		    	BoardEntity.builder().id(10L).registrant(user).title("테스트제목10").content("테스트글10").build(),
		    	BoardEntity.builder().id(11L).registrant(user).title("테스트제목11").content("테스트글11").build(),
		    	BoardEntity.builder().id(12L).registrant(user).title("테스트제목12").content("테스트글12").build()
    	);
    	
    	return boardList;
    }
    
    @Test
    public void getBoardsGridForm() {
    	Pageable page = PageRequest.of(0, 10);
    	Page<BoardEntity> testData = new PageImpl<BoardEntity>(getBoardListData());
    	
    	when(boardRepository.findByTitleContaining(page, null)).thenReturn(testData);
    	
    	try(MockedStatic<GridUtil> gridUtil = Mockito.mockStatic(GridUtil.class)) {
    		when(GridUtil.of(anyInt(), anyLong(), anyList())).thenReturn(new GridForm(true, new GridResponse()));
    		
    		GridForm boardsGridForm = boardService.getBoardsGridForm(page, null);
    	
    		assertThat(boardsGridForm.getData(), notNullValue());
    	}
    }
    
    @Test
    public void getBoard() {
    	UserEntity user =  UserEntity.builder()
				.email(ID)
				.name("채영수")
				.role(Role.GUEST)
				.password("")
				.build();
    	
    	when(boardRepositorySupport.findById(1L)).thenReturn(BoardEntity.builder()
    																	.id(1L)
    																	.registrant(user)
    																	.title("테스트제목1")
    																	.content("테스트글1")
    																	.build());
    	
    	BoardDto board = boardService.getBoard(1L);
    	
    	assertThat(board, notNullValue());
    	
    	verify(boardRepositorySupport, times(1)).findById(1L);
    }
    
    @Test
    public void getReplys() {
    	when(replyRepository.findByBoardId(1L)).thenReturn(getReplyData());
    	
    	List<ReplyDto> replyList = boardService.getReplys(1L);
    	
    	assertThat(replyList.size(), is(5));
    	
    	verify(replyRepository, times(1)).findByBoardId(1L);
    }
    
    @Test
	public void addBoard() {
    	BoardEntity boardEntity = getBoardData();
    	
    	
    	BoardDto boardDto = BoardDto.builder()
					        .id(boardEntity.getId())
					        .title(boardEntity.getTitle())
					        .content(boardEntity.getContent())
					        .registrant(boardEntity.getRegistrant().getEmail())
					        .build();
    	
    	when(boardRepository.save(any(BoardEntity.class))).thenReturn(new BoardEntity(1L, boardEntity.getRegistrant(), "테스트제목", "테스트글"));
    	when(userRepository.findById(ID)).thenReturn(Optional.of(UserEntity.builder()
																			.email(ID)
																			.name("채영수")
																			.role(Role.GUEST)
																			.password("1234")
																			.build()));

    	try(MockedStatic<AppUtil> appUtil = Mockito.mockStatic(AppUtil.class)) {
    		when(AppUtil.getUser()).thenReturn(ID);
    		Long id = boardService.addBoard(new BoardRequest());
    		assertThat(boardDto.getId(), is(id));
    	}
    	verify(boardRepository, times(1)).save(any(BoardEntity.class));
	}
    
    @Test
    public void addReply() {
    	BoardEntity boardEntity = getBoardData();
    	
    	ReplyDto replyDto = ReplyDto.builder()
    							.id(1L)
    							.content("테스트댓글")
    							.boardId(boardEntity.getId())
    							.registrant(boardEntity.getRegistrant().getEmail())
    							.build();
    							
    	when(replyRepository.save(any(ReplyEntity.class))).thenReturn(new ReplyEntity(replyDto.getId(), replyDto.getContent(), boardEntity, boardEntity.getRegistrant()));
    	when(userRepository.findById(ID)).thenReturn(Optional.of(UserEntity.builder()
																			.email(ID)
																			.name("채영수")
																			.role(Role.GUEST)
																			.password("1234")
																			.build()));
    	
    	try(MockedStatic<AppUtil> appUtil = Mockito.mockStatic(AppUtil.class)) {
    		when(AppUtil.getUser()).thenReturn(ID);
	    	Long id = boardService.addReply(replyDto);
	    	assertThat(replyDto.getId(), is(id));
    	}
    	verify(replyRepository, times(1)).save(any(ReplyEntity.class));
    }
}
 