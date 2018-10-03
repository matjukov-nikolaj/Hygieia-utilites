package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.BlackDuck;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.BlackDuckRequest;

public interface BlackDuckService {

    /**
     * Finds all of the BlackDuck data matching the specified request criteria.
     *
     * @param request search criteria
     * @return BlackDuck data matching criteria
     */
    DataResponse<Iterable<BlackDuck>> search(BlackDuckRequest request);
}