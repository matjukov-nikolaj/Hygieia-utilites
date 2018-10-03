package com.capitalone.dashboard.service;

import codesecurity.api.service.CodeSecurityService;
import codesecurity.config.Constants;
import com.capitalone.dashboard.model.AppScan;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.repository.AppScanRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.AppScanRequest;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class AppScanServiceController extends CodeSecurityService<AppScan, AppScanRequest> {

    private final AppScanRepository appScanRepository;
    private final ComponentRepository componentRepository;

    public AppScanServiceController(AppScanRepository appScanRepository,
                                    ComponentRepository componentRepository) {
        this.appScanRepository = appScanRepository;
        this.componentRepository = componentRepository;
    }

    protected String getProjectNameFromObject(AppScan object) { return object.getName(); }

    protected String getAFirstFieldFromMap() { return Constants.AppScan.LOW; }

    protected String getASecondFiledFromMap() { return Constants.AppScan.MEDIUM; }

    protected String getAThirdFieldFromMap() { return Constants.AppScan.HIGH; }

    protected List<AppScan> getDataByCurrentProjectName(String projectName) {
        return appScanRepository.findByProjectName(projectName);
    }

    protected AppScan getResultOfComparison(Map<String, Integer> difference) {
        AppScan result = new AppScan();
        result.setMetrics(difference);
        return result;
    }

    protected Long getProjectTimestamp(AppScan object) { return object.getTimestamp(); }

    protected Integer getMetricField(AppScan object, String metricField) { return object.getMetrics().get(metricField); }

    protected ObjectId getRequestId(AppScanRequest request) {
        return request.getId();
    }

    protected CollectorType getCollectorType() {
        return CollectorType.AppScan;
    }

    protected Component getComponent(ObjectId id) {
        return componentRepository.findOne(id);
    }
}
