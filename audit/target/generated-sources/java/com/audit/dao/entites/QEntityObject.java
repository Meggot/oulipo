package com.audit.dao.entites;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityObject is a Querydsl query type for EntityObject
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QEntityObject extends EntityPathBase<EntityObject> {

    private static final long serialVersionUID = 2021361128L;

    public static final QEntityObject entityObject = new QEntityObject("entityObject");

    public final DateTimePath<java.time.LocalDateTime> creationDate = createDateTime("creationDate", java.time.LocalDateTime.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> oca = createNumber("oca", Integer.class);

    public QEntityObject(String variable) {
        super(EntityObject.class, forVariable(variable));
    }

    public QEntityObject(Path<? extends EntityObject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityObject(PathMetadata metadata) {
        super(EntityObject.class, metadata);
    }

}

