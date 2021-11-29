package com.spring.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.security.model.QUserEntity;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport{
	
	private final JPAQueryFactory queryFactory;
	
	public UserRepositorySupport(JPAQueryFactory queryFactory) {
		super(UserEntity.class);
		this.queryFactory = queryFactory;
	}
	
	public List<UserEntity> findByRole() {
		return queryFactory
				.selectFrom(QUserEntity.userEntity)
				.where(QUserEntity.userEntity.role.eq(Role.ADMIN)).fetch();
	}
}
