package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.BlackDuck;

import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link BlackDuck} data.
 */

public interface BlackDuckRepository extends CrudRepository<BlackDuck, ObjectId>, QueryDslPredicateExecutor<BlackDuck> {
    /**
     * Finds the {@link BlackDuck} data point at the given timestamp for a specific
     * {@link com.capitalone.dashboard.model.CollectorItem}.
     *
     * @param collectorItemId collector item id
     * @param timestamp timestamp
     * @return a {@link BlackDuck}
     */
    BlackDuck findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);
}
