package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.CheckMarx;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link CheckMarx} data.
 */

public interface CheckMarxRepository extends CrudRepository<CheckMarx, ObjectId>, QueryDslPredicateExecutor<CheckMarx> {
    /**
     * Finds the {@link CheckMarx} data point at the given timestamp for a specific
     * {@link com.capitalone.dashboard.model.CollectorItem}.
     *
     * @param collectorItemId collector item id
     * @param timestamp timestamp
     * @return a {@link CheckMarx}
     */
    CheckMarx findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);

    List<CheckMarx> findByCollectorItemIdAndVersionOrderByTimestampDesc (ObjectId collectorItemId,String version);

    List<CheckMarx> findByCollectorItemIdAndNameAndVersionOrderByTimestampDesc (ObjectId collectorItemId,String name,String version);

    List<CheckMarx> findByCollectorItemIdOrderByTimestampDesc (ObjectId collectorItemId);

    List<CheckMarx> findByNameAndVersion(String name,String version);

    List<CheckMarx> findByNameAndVersionOrderByTimestampDesc(String name,String version);

    List<CheckMarx> findByCollectorItemIdAndTimestampIsBetweenOrderByTimestampDesc(ObjectId collectorItemId, long beginDate, long endDate);
}
