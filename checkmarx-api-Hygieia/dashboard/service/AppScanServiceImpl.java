package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.AppScanRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.AppScanRequest;
import codesecurity.api.service.CodeSecurityService;
import com.mysema.query.BooleanBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppScanServiceImpl extends CodeSecurityService<AppScan, AppScanRequest> {

    private final AppScanRepository appScanRepository;
    private final CollectorRepository collectorRepository;
    private final ComponentRepository componentRepository;

    @Autowired
    public AppScanServiceImpl(AppScanRepository appScanRepository,
                                CollectorRepository collectorRepository,
                                ComponentRepository componentRepository) {
        this.appScanRepository = appScanRepository;
        this.collectorRepository = collectorRepository;
        this.componentRepository = componentRepository;
    }

    @Override
    public DataResponse<Iterable<AppScan>> search(AppScanRequest request) {
        if (request == null && request.getType() == null) {
            return emptyResponse();
        }
        return searchType(request);
    }

    @Override
    public DataResponse<Iterable<AppScan>> searchType(AppScanRequest request) {
        CollectorItem item = getCollectorItem(request);
        if (item == null) {
            return emptyResponse();
        }

        QAppScan qAppScan = new QAppScan("appscan");
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qAppScan.collectorItemId.eq(item.getId()));
        Iterable<AppScan> result = appScanRepository.findAll(builder.getValue(), qAppScan.timestamp.desc());
        String instanceUrl = (String)item.getOptions().get("instanceUrl");
        String projectTimestamp = (String) item.getOptions().get("projectTimestamp");
        String reportUrl = getReportURL(instanceUrl,"dashboard/index/", projectTimestamp);
        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();
        return new DataResponse<>(result, lastExecuted, reportUrl);
    }


    protected ObjectId getRequestId(AppScanRequest request) {
        return request.getId();
    }

    protected CollectorType getCollectorType() {
        return CollectorType.AppScan;
    }

    protected DataResponse<Iterable<AppScan>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
    }

    protected Component getComponent(ObjectId id) {
        return componentRepository.findOne(id);
    }
}
