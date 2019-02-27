package com.metadata.dao.entites;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMetric is a Querydsl query type for Metric
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMetric extends EntityPathBase<Metric> {

    private static final long serialVersionUID = 2106924168L;

    public static final QMetric metric = new QMetric("metric");

    public final QEntityObject _super = new QEntityObject(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> creationDate = _super.creationDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> entityId = createNumber("entityId", Integer.class);

    public final EnumPath<com.common.models.dtos.EntityType> entityType = createEnum("entityType", com.common.models.dtos.EntityType.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final NumberPath<Integer> oca = _super.oca;

    public final EnumPath<com.common.models.dtos.MetricType> type = createEnum("type", com.common.models.dtos.MetricType.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QMetric(String variable) {
        super(Metric.class, forVariable(variable));
    }

    public QMetric(Path<? extends Metric> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMetric(PathMetadata metadata) {
        super(Metric.class, metadata);
    }

}

