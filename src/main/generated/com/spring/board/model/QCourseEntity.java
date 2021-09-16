package com.spring.board.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseEntity is a Querydsl query type for CourseEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourseEntity extends EntityPathBase<CourseEntity> {

    private static final long serialVersionUID = -1655365889L;

    public static final QCourseEntity courseEntity = new QCourseEntity("courseEntity");

    public final StringPath col1 = createString("col1");

    public final StringPath col2 = createString("col2");

    public final StringPath col3 = createString("col3");

    public final StringPath col4 = createString("col4");

    public final StringPath col5 = createString("col5");

    public final StringPath courseDiv = createString("courseDiv");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> playTime = createNumber("playTime", Integer.class);

    public final StringPath type = createString("type");

    public QCourseEntity(String variable) {
        super(CourseEntity.class, forVariable(variable));
    }

    public QCourseEntity(Path<? extends CourseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseEntity(PathMetadata metadata) {
        super(CourseEntity.class, metadata);
    }

}

