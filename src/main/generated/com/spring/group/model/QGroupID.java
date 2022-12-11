package com.spring.group.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.spring.course.model.GroupID;


/**
 * QGroupID is a Querydsl query type for GroupID
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QGroupID extends BeanPath<GroupID> {

    private static final long serialVersionUID = 716555250L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupID groupID = new QGroupID("groupID");

    public final com.spring.course.model.QCourseEntity course;

    public final com.spring.security.model.QUserEntity member;

    public QGroupID(String variable) {
        this(GroupID.class, forVariable(variable), INITS);
    }

    public QGroupID(Path<? extends GroupID> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupID(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupID(PathMetadata metadata, PathInits inits) {
        this(GroupID.class, metadata, inits);
    }

    public QGroupID(Class<? extends GroupID> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.course = inits.isInitialized("course") ? new com.spring.course.model.QCourseEntity(forProperty("course"), inits.get("course")) : null;
        this.member = inits.isInitialized("member") ? new com.spring.security.model.QUserEntity(forProperty("member")) : null;
    }

}

