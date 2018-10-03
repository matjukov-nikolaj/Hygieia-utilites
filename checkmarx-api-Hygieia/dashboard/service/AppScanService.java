package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.AppScan;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.AppScanRequest;

public interface AppScanService {

    /**
     * Finds all of the AppScan data matching the specified request criteria.
     *
     * @param request search criteria
     * @return AppScan data matching criteria
     */
    DataResponse<Iterable<AppScan>> search(AppScanRequest request);
}