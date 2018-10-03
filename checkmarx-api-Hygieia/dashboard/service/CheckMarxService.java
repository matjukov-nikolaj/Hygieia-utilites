package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.CheckMarx;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.CheckMarxRequest;

public interface CheckMarxService {

    /**
     * Finds all of the CheckMarx data matching the specified request criteria.
     *
     * @param request search criteria
     * @return CheckMarx data matching criteria
     */
    DataResponse<Iterable<CheckMarx>> search(CheckMarxRequest request);
}