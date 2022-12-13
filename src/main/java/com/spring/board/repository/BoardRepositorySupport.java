package com.spring.board.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.QBoardEntity;
import com.spring.board.model.QBoardFileEntity;
import com.spring.board.model.QReplyEntity;

@Repository
public class BoardRepositorySupport extends QuerydslRepositorySupport{
	
	private final JPAQueryFactory queryFactory;
	QBoardEntity boardEntity = QBoardEntity.boardEntity;
	QReplyEntity replyEntity = QReplyEntity.replyEntity;
	QBoardFileEntity boardFileEntity = QBoardFileEntity.boardFileEntity;
	
	public BoardRepositorySupport(JPAQueryFactory queryFactory) {
		super(BoardEntity.class);
		this.queryFactory = queryFactory;
	}
	
	public BoardEntity findById(Long id) {
		return queryFactory
				.selectFrom(boardEntity)
				.leftJoin(boardFileEntity).on(boardEntity.id.eq(boardFileEntity.board.id))
				.fetchJoin()
				.where(boardEntity.id.eq(id))
				.fetchOne();
	}
}
