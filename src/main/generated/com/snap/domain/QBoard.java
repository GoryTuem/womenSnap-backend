package com.snap.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 2116482451L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final ListPath<BoardFile, QBoardFile> boardFiles = this.<BoardFile, QBoardFile>createList("boardFiles", BoardFile.class, QBoardFile.class, PathInits.DIRECT2);

    public final ListPath<BoardTag, QBoardTag> boardTags = this.<BoardTag, QBoardTag>createList("boardTags", BoardTag.class, QBoardTag.class, PathInits.DIRECT2);

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final ListPath<MemberLike, QMemberLike> memberLikes = this.<MemberLike, QMemberLike>createList("memberLikes", MemberLike.class, QMemberLike.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> modify_date = createDateTime("modify_date", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> reg_date = createDateTime("reg_date", java.time.LocalDateTime.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

