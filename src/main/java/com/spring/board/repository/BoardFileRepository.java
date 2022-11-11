package com.spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.board.model.BoardFileEntity;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}