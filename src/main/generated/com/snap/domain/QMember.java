package com.snap.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1492474253L;

    public static final QMember member = new QMember("member1");

    public final StringPath mem_email = createString("mem_email");

    public final DateTimePath<java.time.LocalDateTime> mem_join_date = createDateTime("mem_join_date", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> mem_modify_date = createDateTime("mem_modify_date", java.time.LocalDateTime.class);

    public final StringPath mem_name = createString("mem_name");

    public final StringPath mem_password = createString("mem_password");

    public final StringPath mem_phone = createString("mem_phone");

    public final StringPath mem_receive_yn = createString("mem_receive_yn");

    public final EnumPath<RoleType> mem_role = createEnum("mem_role", RoleType.class);

    public final EnumPath<MemberTypes> mem_type = createEnum("mem_type", MemberTypes.class);

    public final StringPath memNickname = createString("memNickname");

    public final StringPath refreshToken = createString("refreshToken");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

