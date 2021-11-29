package com.spring.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.board.model.ReplyEntity;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
	List<ReplyEntity> findByBoardId(Long boardId);
}