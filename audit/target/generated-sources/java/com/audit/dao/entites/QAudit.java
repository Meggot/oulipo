package com.audit.dao.entites;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAudit is a Querydsl query type for Audit
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAudit extends EntityPathBase<Audit> {

    private static final long serialVersionUID = 1775593909L;

    public static final QAudit audit = new QAudit("audit");

    public final QEntityObject _super = new QEntityObject(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> creationDate = _super.creationDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> entityId = createNumber("entityId", Integer.class);

    public final EnumPath<com.common.models.messages.MessageType> eventType = createEnum("eventType", com.common.models.messages.MessageType.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final NumberPath<Integer> oca = _super.oca;

    public final NumberPath<Integer> originUserId = createNumber("originUserId", Integer.class);

    public final StringPath value = createString("value");

    public QAudit(String variable) {
        super(Audit.class, forVariable(variable));
    }

    public QAudit(Path<? extends Audit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAudit(PathMetadata metadata) {
        super(Audit.class, metadata);
    }

}

