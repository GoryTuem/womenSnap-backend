package com.snap.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardFileId is a Querydsl query type for BoardFileId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBoardFileId extends BeanPath<BoardFileId> {

    private static final long serialVersionUID = -33769686L;

    public static final QBoardFileId boardFileId = new QBoardFileId("boardFileId");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public QBoardFileId(String variable) {
        super(BoardFileId.class, forVariable(variable));
    }

    public QBoardFileId(Path<? extends BoardFileId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardFileId(PathMetadata metadata) {
        super(BoardFileId.class, metadata);
    }

}

