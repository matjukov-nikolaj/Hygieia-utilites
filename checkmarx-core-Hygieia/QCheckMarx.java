package com.capitalone.dashboard.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCheckMarx is a Querydsl query type for CheckMarx
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCheckMarx extends EntityPathBase<CheckMarx> {

    private static final long serialVersionUID = -531924384L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckMarx checkMarx = new QCheckMarx("checkMarx");

    public final QBaseModel _super;

    public final org.bson.types.QObjectId buildId;

    public final org.bson.types.QObjectId collectorItemId;

    // inherited
    public final org.bson.types.QObjectId id;

    public final MapPath<String, Integer, NumberPath<Integer>> metrics = this.<String, Integer, NumberPath<Integer>>createMap("metrics", String.class, Integer.class, NumberPath.class);

    public final StringPath name = createString("name");

    public final StringPath projectName = createString("projectName");

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final StringPath url = createString("url");

    public final StringPath version = createString("version");

    public QCheckMarx(String variable) {
        this(CheckMarx.class, forVariable(variable), INITS);
    }

    public QCheckMarx(Path<? extends CheckMarx> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCheckMarx(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCheckMarx(PathMetadata<?> metadata, PathInits inits) {
        this(CheckMarx.class, metadata, inits);
    }

    public QCheckMarx(Class<? extends CheckMarx> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseModel(type, metadata, inits);
        this.buildId = inits.isInitialized("buildId") ? new org.bson.types.QObjectId(forProperty("buildId")) : null;
        this.collectorItemId = inits.isInitialized("collectorItemId") ? new org.bson.types.QObjectId(forProperty("collectorItemId")) : null;
        this.id = _super.id;
    }

}

