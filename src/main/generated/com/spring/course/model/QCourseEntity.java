package com.spring.course.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseEntity is a Querydsl query type for CourseEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourseEntity extends EntityPathBase<CourseEntity> {

    private static final long serialVersionUID = 2034200962L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourseEntity courseEntity = new QCourseEntity("courseEntity");

    public final com.spring.common.model.QBaseTimeEntity _super = new com.spring.common.model.QBaseTimeEntity(this);

    public final NumberPath<Long> category = createNumber("category", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> curNum = createNumber("curNum", Integer.class);

    public final NumberPath<Integer> divclsNo = createNumber("divclsNo", Integer.class);

    public final StringPath edge = createString("edge");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxNum = createNumber("maxNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath node = createString("node");

    public final com.spring.security.model.QUserEntity registrant;

    public final StringPath title = createString("title");

    public QCourseEntity(String variable) {
        this(CourseEntity.class, forVariable(variable), INITS);
    }

    public QCourseEntity(Path<? extends CourseEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourseEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourseEntity(PathMetadata metadata, PathInits inits) {
        this(CourseEntity.class, metadata, inits);
    }

    public QCourseEntity(Class<? extends CourseEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.registrant = inits.isInitialized("registrant") ? new com.spring.security.model.QUserEntity(forProperty("registrant")) : null;
    }

}

