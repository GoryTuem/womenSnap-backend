package com.snap.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotiBoard is a Querydsl query type for NotiBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotiBoard extends EntityPathBase<NotiBoard> {

    private static final long serialVersionUID = 1325449405L;

    public static final QNotiBoard notiBoard = new QNotiBoard("notiBoard");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QNotiBoard(String variable) {
        super(NotiBoard.class, forVariable(variable));
    }

    public QNotiBoard(Path<? extends NotiBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotiBoard(PathMetadata metadata) {
        super(NotiBoard.class, metadata);
    }

}

