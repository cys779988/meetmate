package com.spring.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseFileEntity is a Querydsl query type for BaseFileEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseFileEntity extends EntityPathBase<BaseFileEntity> {

    private static final long serialVersionUID = 653568772L;

    public static final QBaseFileEntity baseFileEntity = new QBaseFileEntity("baseFileEntity");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath originalFileName = createString("originalFileName");

    public final StringPath saveFileName = createString("saveFileName");

    public QBaseFileEntity(String variable) {
        super(BaseFileEntity.class, forVariable(variable));
    }

    public QBaseFileEntity(Path<? extends BaseFileEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseFileEntity(PathMetadata metadata) {
        super(BaseFileEntity.class, metadata);
    }

}

