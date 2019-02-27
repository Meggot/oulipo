package com.user.dao.entites;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPassword is a Querydsl query type for Password
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPassword extends EntityPathBase<Password> {

    private static final long serialVersionUID = 41940439L;

    public static final QPassword password = new QPassword("password");

    public final QEntityObject _super = new QEntityObject(this);

    //inherited
    public final DateTimePath<java.util.Date> creationDate = _super.creationDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath hashValue = createString("hashValue");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> modifiedDate = _super.modifiedDate;

    //inherited
    public final NumberPath<Integer> oca = _super.oca;

    public final StringPath salt = createString("salt");

    public QPassword(String variable) {
        super(Password.class, forVariable(variable));
    }

    public QPassword(Path<? extends Password> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPassword(PathMetadata metadata) {
        super(Password.class, metadata);
    }

}

