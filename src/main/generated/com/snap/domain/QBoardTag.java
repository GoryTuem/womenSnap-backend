package com.snap.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardTag is a Querydsl query type for BoardTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardTag extends EntityPathBase<BoardTag> {

    private static final long serialVersionUID = 2008876295L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardTag boardTag = new QBoardTag("boardTag");

    public final QBoard board;

    public final DatePath<java.time.LocalDate> created = createDate("created", java.time.LocalDate.class);

    public final QBoardFileId id;

    public final QTag tag;

    public QBoardTag(String variable) {
        this(BoardTag.class, forVariable(variable), INITS);
    }

    public QBoardTag(Path<? extends BoardTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardTag(PathMetadata metadata, PathInits inits) {
        this(BoardTag.class, metadata, inits);
    }

    public QBoardTag(Class<? extends BoardTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.id = inits.isInitialized("id") ? new QBoardFileId(forProperty("id")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

