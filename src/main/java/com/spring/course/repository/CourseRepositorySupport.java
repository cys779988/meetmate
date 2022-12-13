package com.spring.course.repository;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.course.model.ApplyCourseRequest;
import com.spring.course.model.CourseEntity;
import com.spring.course.model.QCourseEntity;
import com.spring.group.model.QGroupEntity;

@Repository
public class CourseRepositorySupport extends QuerydslRepositorySupport {
	
	private final EntityManager entityManager;
	private final JPAQueryFactory queryFactory;
	QCourseEntity courseEntity =  QCourseEntity.courseEntity;
	QGroupEntity groupEntity =  QGroupEntity.groupEntity;
	
	public CourseRepositorySupport(JPAQueryFactory queryFactory, EntityManager entityManager) {
		super(CourseEntity.class);
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
	}
	
	public CourseEntity updateCurNum(Long no) {
		queryFactory.update(courseEntity)
					.set(courseEntity.curNum, courseEntity.curNum.add(1))
					.where(courseEntity.id.eq(no))
					.execute();
		
		entityManager.flush();
		entityManager.clear();
		
		return queryFactory.selectFrom(courseEntity)
							.where(courseEntity.id.eq(no)).fetchOne();
	}

	public PageImpl<CourseEntity> findApplyCourseByCategory(ApplyCourseRequest request) {
		Pageable page = request.getPage();
		
		JPQLQuery<CourseEntity> query = queryFactory.select(courseEntity)
													.from(groupEntity)
													.innerJoin(courseEntity).on(courseEntity.id.eq(groupEntity.id.course.id))
													.fetchJoin()
													.where(groupEntity.id.member.email.eq(request.getUserId())
															, eqCategory(request.getCategory()));
		 
		long totalCount = query.fetchCount();
		
		List<CourseEntity> courseList = getQuerydsl().applyPagination(page, query).fetch();
		
		return new PageImpl<>(courseList, page, totalCount);
	}
	
	private BooleanExpression eqCategory(Long category) {
		if(Objects.isNull(category)) {
			return null;
		}
		
		return courseEntity.category.eq(category); 
	}
}
