package com.spring.board.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.board.model.BoardDto;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.ReplyRepository;
import com.spring.common.model.GridForm;
import com.spring.common.util.GridUtil;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;
    private ReplyRepository replyRepository;
    private BoardRepositorySupport boardRepositorySupport;
    
    @Transactional
    public GridForm getBoardsGridForm(Pageable page, String keyword) {
    	Page<BoardEntity> pageBoards = boardRepository.findByTitleContaining(PageRequest.of(
    																		page.getPageNumber()
    																		, page.getPageSize()
    																		, page.getSort())
    																	, keyword);
    	List<BoardDto> contentList = new ArrayList<>();
    	
    	if(pageBoards.hasContent()) {
    		contentList = pageBoards.getContent().stream().map(i -> convertEntityToDto(i)).collect(Collectors.toList());
    	}
    	
        return GridUtil.of(page.getPageNumber(), pageBoards.getTotalElements(), contentList);
    }
    
    @Transactional
    public BoardDto getBoard(Long id) {
        Optional<BoardEntity> entityWrapper = boardRepository.findById(id);
        BoardDto board = new BoardDto();
        if(entityWrapper.isPresent()) {
        	BoardEntity boardEntity = entityWrapper.get();
        	board = convertEntityToDto(boardEntity);
        }
        
        return board;
    }
    
    @Transactional
	public List<ReplyEntity> getReplys(Long no) {
    	List<ReplyEntity> replyEntityList = replyRepository.findByBoardId(no);
		return replyEntityList;
	}
	
    @Transactional
    public Long addBoard(BoardDto boardDto) {
    	BoardEntity entity = boardDto.toEntity();
        return boardRepository.save(entity).getId();
    }

    @Transactional
	public Long addReply(ReplyDto replyDto) {
    	ReplyEntity entity = replyDto.toEntity();
    	entity.setBoard(BoardEntity.builder().id(replyDto.getBoardId()).build());
        return replyRepository.save(entity).getId();
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
}
