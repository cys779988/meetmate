package com.spring.code.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonCodeEntity is a Querydsl query type for CommonCodeEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommonCodeEntity extends EntityPathBase<CommonCodeEntity> {

    private static final long serialVersionUID = 1667065101L;

    public static final QCommonCodeEntity commonCodeEntity = new QCommonCodeEntity("commonCodeEntity");

    public final com.spring.common.model.QBaseTimeEntity _super = new com.spring.common.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath registrant = createString("registrant");

    public final EnumPath<CodeType> type = createEnum("type", CodeType.class);

    public QCommonCodeEntity(String variable) {
        super(CommonCodeEntity.class, forVariable(variable));
    }

    public QCommonCodeEntity(Path<? extends CommonCodeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonCodeEntity(PathMetadata metadata) {
        super(CommonCodeEntity.class, metadata);
    }

}

