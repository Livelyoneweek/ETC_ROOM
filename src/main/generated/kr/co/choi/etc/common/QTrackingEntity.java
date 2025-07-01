package kr.co.choi.etc.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.dsl.StringTemplate;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrackingEntity is a Querydsl query type for TrackingEntity
 */
@SuppressWarnings("this-escape")
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QTrackingEntity extends EntityPathBase<TrackingEntity> {

    private static final long serialVersionUID = 465486319L;

    public static final QTrackingEntity trackingEntity = new QTrackingEntity("trackingEntity");

    public final ComparablePath<java.util.UUID> createdBy = createComparable("createdBy", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> lastModifiedBy = createComparable("lastModifiedBy", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public QTrackingEntity(String variable) {
        super(TrackingEntity.class, forVariable(variable));
    }

    public QTrackingEntity(Path<? extends TrackingEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrackingEntity(PathMetadata metadata) {
        super(TrackingEntity.class, metadata);
    }

}

