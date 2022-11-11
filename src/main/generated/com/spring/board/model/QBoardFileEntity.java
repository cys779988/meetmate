package com.spring.board.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardFileEntity is a Querydsl query type for BoardFileEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoardFileEntity extends EntityPathBase<BoardFileEntity> {

    private static final long serialVersionUID = 1557859652L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardFileEntity boardFileEntity = new QBoardFileEntity("boardFileEntity");

    public final com.spring.common.model.QBaseFileEntity _super = new com.spring.common.model.QBaseFileEntity(this);

    public final QBoardEntity board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath filePath = _super.filePath;

    //inherited
    public final NumberPath<Long> fileSize = _super.fileSize;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath originalFileName = _super.originalFileName;

    public final com.spring.security.model.QUserEntity registrant;

    //inherited
    public final StringPath saveFileName = _super.saveFileName;

    public QBoardFileEntity(String variable) {
        this(BoardFileEntity.class, forVariable(variable), INITS);
    }

    public QBoardFileEntity(Path<? extends BoardFileEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardFileEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardFileEntity(PathMetadata metadata, PathInits inits) {
        this(BoardFileEntity.class, metadata, inits);
    }

    public QBoardFileEntity(Class<? extends BoardFileEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoardEntity(forProperty("board"), inits.get("board")) : null;
        this.registrant = inits.isInitialized("registrant") ? new com.spring.security.model.QUserEntity(forProperty("registrant")) : null;
    }

}

