package com.spring.board.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardEntity is a Querydsl query type for BoardEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardEntity extends EntityPathBase<BoardEntity> {

    private static final long serialVersionUID = -405249240L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardEntity boardEntity = new QBoardEntity("boardEntity");

    public final com.spring.common.model.QBaseTimeEntity _super = new com.spring.common.model.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.spring.security.model.QUserEntity registrant;

    public final ListPath<ReplyEntity, QReplyEntity> reply = this.<ReplyEntity, QReplyEntity>createList("reply", ReplyEntity.class, QReplyEntity.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QBoardEntity(String variable) {
        this(BoardEntity.class, forVariable(variable), INITS);
    }

    public QBoardEntity(Path<? extends BoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardEntity(PathMetadata metadata, PathInits inits) {
        this(BoardEntity.class, metadata, inits);
    }

    public QBoardEntity(Class<? extends BoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.registrant = inits.isInitialized("registrant") ? new com.spring.security.model.QUserEntity(forProperty("registrant")) : null;
    }

}

