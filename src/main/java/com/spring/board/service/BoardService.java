package com.spring.board.service;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.spring.board.model.BoardDto;
import com.spring.board.model.BoardEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;
    private BoardRepositorySupport boardRepositorySupport;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    //@Cacheable(key = "#pageNum", value = "getBoardList")
    @Transactional
    public List<BoardDto> getBoardList(Integer pageNum, String keyword) {
    	Page<BoardEntity> page = boardRepository.findByTitleContaining(PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Direction.ASC, "createdDate")), keyword);
        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for ( BoardEntity boardEntity : boardEntities) {
        	boardDtoList.add(this.convertEntityToDto(boardEntity));
        }
        return boardDtoList;
    }
    
    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDto boardDTO = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();

        return boardDTO;
    }

    @Transactional
    public Long add(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
    
    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
    
    @Transactional
    public Long getBoardCount(String keyword) {
        return boardRepositorySupport.findCountByTitle(keyword);
    }

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

}
