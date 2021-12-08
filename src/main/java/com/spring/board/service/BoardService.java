package com.spring.board.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.spring.board.model.BoardDto;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.ReplyRepository;
import com.spring.common.model.PageVO;
import com.spring.common.util.AppUtil;
import com.spring.security.model.UserEntity;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;
    private ReplyRepository replyRepository;
    private BoardRepositorySupport boardRepositorySupport;
    
    //@Cacheable(key = "#pageNum", value = "getBoardList")
    @Transactional
    public List<BoardDto> getBoards(PageVO pageVO, String keyword) {
    	Page<BoardEntity> page = boardRepository.findByTitleContaining(PageRequest.of(pageVO.getPage(), pageVO.getPerPage(), Sort.by(Direction.ASC, "createdDate")), keyword);
        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();
        
        for ( BoardEntity boardEntity : boardEntities) {
        	boardDtoList.add(this.convertEntityToDto(boardEntity));
        }
        
        return boardDtoList;
    }
    
    @Transactional
    public Long getBoardCount(String keyword) {
        return boardRepositorySupport.findCountByTitle(keyword);
    }
    
    @Transactional
    public BoardDto getBoard(Long id) {
        Optional<BoardEntity> entityWrapper = boardRepository.findById(id);
        BoardDto boardDto = new BoardDto();
        if(entityWrapper.isPresent()) {
        	BoardEntity boardEntity = entityWrapper.get();
        	boardDto = convertEntityToDto(boardEntity);
        }
        
        return boardDto;
    }
    
    @Transactional
	public List<ReplyDto> getReplys(Long no) {
    	List<ReplyEntity> replyEntityList = replyRepository.findByBoardId(no);
    	List<ReplyDto> replyDtoList = new ArrayList<>();
    	replyEntityList.forEach(entity -> {
    		replyDtoList.add(convertEntityToDto(entity));
    	});
		return replyDtoList;
	}
	
    @Transactional
    public Long addBoard(BoardDto boardDto) {
    	BoardEntity entity = boardDto.toEntity();
    	entity.setRegistrant(UserEntity.builder().email(AppUtil.getUser()).build());
        return boardRepository.save(entity).getId();
    }

    @Transactional
	public void addReply(ReplyDto replyDto) {
    	ReplyEntity entity = replyDto.toEntity();
    	entity.setRegistrant(UserEntity.builder().email(AppUtil.getUser()).build());
    	entity.setBoard(BoardEntity.builder().id(replyDto.getBoardId()).build());
        replyRepository.save(entity);
	}
	
    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
    
    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .registrant(boardEntity.getRegistrant().getEmail())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
    
    private ReplyDto convertEntityToDto(ReplyEntity replyEntity) {
    	return ReplyDto.builder()
    			.id(replyEntity.getId())
    			.boardId(replyEntity.getBoard().getId())
    			.registrant(replyEntity.getRegistrant().getEmail())
    			.content(replyEntity.getContent())
    			.createdDate(replyEntity.getCreatedDate())
    			.build();
    }

    /*
    public Integer[] getPageList(Integer curPageNum, String keyword) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount(keyword));

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        int val = curPageNum;
        int idx = 0;
        while (pageList[BLOCK_PAGE_NUM_COUNT-1]==null) {
        	if(val > totalLastPageNum) {
        		break;
        	}
			pageList[idx++] = val++;
		}
        return pageList;
    }
	*/
}
