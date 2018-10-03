package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.AppScan;

import org.bson.types.ObjectId;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for {@link AppScan} data.
 */

public interface AppScanRepository extends CrudRepository<AppScan, ObjectId>, QueryDslPredicateExecutor<AppScan> {
    /**
     * Finds the {@link AppScan} data point at the given timestamp for a specific
     * {@link com.capitalone.dashboard.model.CollectorItem}.
     *
     * @param collectorItemId collector item id
     * @param timestamp timestamp
     * @return a {@link AppScan}
     */
    AppScan findByCollectorItemIdAndTimestamp(ObjectId collectorItemId, long timestamp);

    /**
     * Finds the {@link List<AppScan>}
     * {@link com.capitalone.dashboard.model.CollectorItem}.
     *
     * @param projectName project name
     *
     * @return a {@link List<AppScan>}
     */
    List<AppScan> findByProjectName(String projectName);

}
