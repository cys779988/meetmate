package com.spring.board.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.board.model.BoardEntity;
import com.spring.board.model.QBoardEntity;

@Repository
public class BoardRepositorySupport extends QuerydslRepositorySupport{
	
	private final JPAQueryFactory queryFactory;
	QBoardEntity boardEntity = QBoardEntity.boardEntity;
	public BoardRepositorySupport(JPAQueryFactory queryFactory) {
		super(BoardEntity.class);
		this.queryFactory = queryFactory;
	}
	
	public Long findCountByTitle(String title) {
		return queryFactory
				.selectFrom(boardEntity)
				.where(boardEntity.title.like(title+"%"))
				.fetchCount();
	}
}
