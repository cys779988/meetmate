package com.spring.board.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.board.model.BoardDto;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.BoardFileEntity;
import com.spring.board.model.BoardRequest;
import com.spring.board.model.ReplyDto;
import com.spring.board.model.ReplyEntity;
import com.spring.board.repository.BoardFileRepository;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.BoardRepositorySupport;
import com.spring.board.repository.ReplyRepository;
import com.spring.common.exception.BusinessException;
import com.spring.common.model.ErrorCode;
import com.spring.common.model.GridForm;
import com.spring.common.util.AppUtil;
import com.spring.common.util.GridUtil;
import com.spring.file.model.FileDirectory;
import com.spring.file.model.FileDto;
import com.spring.file.util.FileHandler;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final ReplyRepository replyRepository;
    private final BoardRepositorySupport boardRepositorySupport;
    private final UserRepository userRepository;
    
    @Transactional
    public GridForm getBoardsGridForm(Pageable page, String keyword) {
    	Page<BoardEntity> pageBoards = boardRepository.findByTitleContaining(PageRequest.of(
    																		page.getPageNumber()
    																		, page.getPageSize()
    																		, page.getSort())
    																	, keyword);
    	List<BoardDto> contentList = new ArrayList<>();
    	
    	if(pageBoards.hasContent()) {
    		contentList = pageBoards.getContent().stream().map(i -> convertBoardEntityToDto(i)).collect(Collectors.toList());
    	}
    	
        return GridUtil.of(page.getPageNumber(), pageBoards.getTotalElements(), contentList);
    }
    
    @Transactional
    public BoardDto getBoard(Long id) {
        BoardEntity boardEntity = boardRepositorySupport.findById(id);
        boardEntity.getFiles();
        BoardDto board = new BoardDto();
        if(Objects.nonNull(boardEntity)) {
        	board = convertBoardEntityToDto(boardEntity);
        }
        
        return board;
    }
    
    @Transactional
	public List<ReplyDto> getReplys(Long no) {
    	List<ReplyEntity> replyEntityList = replyRepository.findByBoardId(no);
    	List<ReplyDto> replyList = replyEntityList.stream().map(i -> convertReplyEntityToDto(i)).collect(Collectors.toList());

    	return replyList;
	}
    
    @Transactional
    public Long addBoard(BoardRequest boardRequest) {
    	Optional<UserEntity> user = userRepository.findById(AppUtil.getUser());

    	if(!boardRequest.getDeleteFiles().isEmpty()) {
    		List<BoardFileEntity> boardFileEntityList = boardFileRepository.findAllById(boardRequest.getDeleteFiles());
    		boardFileRepository.deleteAll(boardFileEntityList);
			
    		List<FileDto> boardFileList = boardFileEntityList.stream().map(i -> convertFileEntityToDto(i)).collect(Collectors.toList());
    		FileHandler.deleteFiles(boardFileList);
    	}
    	
    	BoardEntity boardEntity = BoardEntity.builder()
    										.id(boardRequest.getBoardId())
    										.registrant(user.get())
    										.title(boardRequest.getTitle())
    										.content(boardRequest.getContent())
    										.build();
    	
    	List<MultipartFile> fileList = boardRequest.getFiles();
    	if(Objects.nonNull(fileList) && !fileList.isEmpty()) {
    		addBoardFiles(boardEntity, fileList, user.get());
    	}
    	
        return boardRepository.save(boardEntity).getId();
    }

    @Transactional
    public List<BoardFileEntity> addBoardFiles(BoardEntity boardEntity, List<MultipartFile> fileList, UserEntity user) {
    	List<FileDto> fileInfoList = FileHandler.uploadFileAndParseFileInfo(fileList, FileDirectory.BOARD);
    	
		List<BoardFileEntity> fileEntityList = fileInfoList.stream().map(i -> BoardFileEntity.builder()
																				.filePath(i.getFilePath())
																				.fileSize(i.getFileSize())
																				.board(boardEntity)
																				.originalFileName(i.getOriginalFileName())
																				.saveFileName(i.getSaveFileName())
																				.registrant(user)
																				.build())
																	.collect(Collectors.toList());
    	return boardFileRepository.saveAll(fileEntityList);
    }
    
    @Transactional
	public Long addReply(ReplyDto replyDto) {
    	Optional<UserEntity> user = userRepository.findById(AppUtil.getUser());
    	
    	Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(replyDto.getBoardId());
    	
    	ReplyEntity replyEntity = replyDto.toEntity(user.get());
    	
    	if(boardEntityWrapper.isPresent()) {
    		replyEntity.setBoard(boardEntityWrapper.get());
    	}
    	
        return replyRepository.save(replyEntity).getId();
	}
	
    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
    
	public FileDto getBoardFile(Long no) {
		Optional<BoardFileEntity> boardFileEntity = boardFileRepository.findById(no);
		
		if(!boardFileEntity.isPresent()) {
			throw new BusinessException(ErrorCode.NOTFOUND_FILE);
		}
		
		return convertFileEntityToDto(boardFileEntity.get());
	}
	
    private BoardDto convertBoardEntityToDto(BoardEntity boardEntity) {
    	List<FileDto> fileList = boardEntity.getFiles().stream().map(i -> convertFileEntityToDto(i)).collect(Collectors.toList());
    	
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .fileList(fileList)
                .registrant(boardEntity.getRegistrant().getEmail())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
    
    private ReplyDto convertReplyEntityToDto(ReplyEntity replyEntity) {
    	return ReplyDto.builder()
    			.id(replyEntity.getId())
    			.boardId(replyEntity.getBoard().getId())
    			.registrant(replyEntity.getRegistrant().getEmail())
    			.content(replyEntity.getContent())
    			.createdDate(replyEntity.getCreatedDate())
    			.build();
    }
    
    private FileDto convertFileEntityToDto(BoardFileEntity fileEntity) {
    	return FileDto.builder()
    				.id(fileEntity.getId())
	    			.filePath(fileEntity.getFilePath())
	    			.fileSize(fileEntity.getFileSize())
	    			.originalFileName(fileEntity.getOriginalFileName())
	    			.saveFileName(fileEntity.getSaveFileName())
    				.build();
    }

}
