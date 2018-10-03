package com.capitalone.dashboard.service;

import codesecurity.api.service.CodeSecurityService;
import codesecurity.config.Constants;
import com.capitalone.dashboard.model.CheckMarx;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.repository.CheckMarxRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.CheckMarxRequest;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class CheckMarxServiceController extends CodeSecurityService<CheckMarx, CheckMarxRequest> {

    private final CheckMarxRepository checkMarxRepository;
    private final ComponentRepository componentRepository;

    public CheckMarxServiceController(CheckMarxRepository checkMarxRepository,
                                    ComponentRepository componentRepository) {
        this.checkMarxRepository = checkMarxRepository;
        this.componentRepository = componentRepository;
    }

    protected String getProjectNameFromObject(CheckMarx object) { return object.getName(); }

    protected String getAFirstFieldFromMap() { return Constants.CheckMarx.LOW; }

    protected String getASecondFiledFromMap() { return Constants.CheckMarx.MEDIUM; }

    protected String getAThirdFieldFromMap() { return Constants.CheckMarx.HIGH; }

    protected List<CheckMarx> getDataByCurrentProjectName(String projectName) {
        return checkMarxRepository.findByProjectName(projectName);
    }

    protected CheckMarx getResultOfComparison(Map<String, Integer> difference) {
        CheckMarx result = new CheckMarx();
        result.setMetrics(difference);
        return result;
    }

    protected Long getProjectTimestamp(CheckMarx object) { return object.getTimestamp(); }

    protected Integer getMetricField(CheckMarx object, String metricField) { return object.getMetrics().get(metricField); }

    protected ObjectId getRequestId(CheckMarxRequest request) {
        return request.getId();
    }

    protected CollectorType getCollectorType() {
        return CollectorType.CheckMarx;
    }

    protected Component getComponent(ObjectId id) {
        return componentRepository.findOne(id);
    }


}
