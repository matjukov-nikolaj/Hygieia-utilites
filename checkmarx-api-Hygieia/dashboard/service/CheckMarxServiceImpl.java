package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.CheckMarxRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.CheckMarxRequest;
import codesecurity.api.service.CodeSecurityService;
import com.mysema.query.BooleanBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckMarxServiceImpl extends CodeSecurityService<CheckMarx, CheckMarxRequest> {
    private final CheckMarxRepository checkMarxRepository;
    private final ComponentRepository componentRepository;
    private final CollectorRepository collectorRepository;

    @Autowired
    public CheckMarxServiceImpl(CheckMarxRepository checkMarxRepository,
                                ComponentRepository componentRepository,
                                CollectorRepository collectorRepository) {
        this.checkMarxRepository = checkMarxRepository;
        this.componentRepository = componentRepository;
        this.collectorRepository = collectorRepository;
    }

    @Override
    public DataResponse<Iterable<CheckMarx>> search(CheckMarxRequest request) {
        if (request == null && request.getType() == null) {
            return emptyResponse();
        }
        return searchType(request);
    }

    public DataResponse<Iterable<CheckMarx>> searchType(CheckMarxRequest request) {
        CollectorItem item = getCollectorItem(request);
        if (item == null) {
            return emptyResponse();
        }

        QCheckMarx security = new QCheckMarx("checkmarx");
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(security.collectorItemId.eq(item.getId()));
        Iterable<CheckMarx> result = checkMarxRepository.findAll(builder.getValue(), security.timestamp.desc());
        String instanceUrl = (String)item.getOptions().get("instanceUrl");
        String projectId = (String) item.getOptions().get("projectId");
        String reportUrl = getReportURL(instanceUrl,"dashboard/index/",projectId);
        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();
        return new DataResponse<>(result, lastExecuted, reportUrl);
    }

    protected ObjectId getRequestId(CheckMarxRequest request) {
        return request.getId();
    }

    protected CollectorType getCollectorType() {
        return CollectorType.CheckMarx;
    }

    protected DataResponse<Iterable<CheckMarx>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
    }

    protected Component getComponent(ObjectId id) {
        return componentRepository.findOne(id);
    }

}
