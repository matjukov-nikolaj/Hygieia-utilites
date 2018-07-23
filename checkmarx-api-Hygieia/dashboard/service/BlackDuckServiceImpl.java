package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.BlackDuckRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.BlackDuckRequest;
import codesecurity.api.service.CodeSecurityService;
import com.mysema.query.BooleanBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackDuckServiceImpl extends CodeSecurityService<BlackDuck, BlackDuckRequest> {

    private final BlackDuckRepository blackDuckRepository;
    private final CollectorRepository collectorRepository;
    private final ComponentRepository componentRepository;

    @Autowired
    public BlackDuckServiceImpl(BlackDuckRepository blackDuckRepository,
                               CollectorRepository collectorRepository,
                                ComponentRepository componentRepository) {
        this.blackDuckRepository = blackDuckRepository;
        this.collectorRepository = collectorRepository;
        this.componentRepository = componentRepository;
    }

    @Override
    public DataResponse<Iterable<BlackDuck>> search(BlackDuckRequest request) {
        if (request == null && request.getType() == null) {
            return emptyResponse();
        }
        return searchType(request);
    }

    @Override
    public DataResponse<Iterable<BlackDuck>> searchType(BlackDuckRequest request) {
        CollectorItem item = getCollectorItem(request);
        if (item == null) {
            return emptyResponse();
        }

        QBlackDuck qBlackDuck = new QBlackDuck("blackduck");
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qBlackDuck.collectorItemId.eq(item.getId()));
        Iterable<BlackDuck> result = blackDuckRepository.findAll(builder.getValue(), qBlackDuck.timestamp.desc());
        String instanceUrl = (String)item.getOptions().get("instanceUrl");
        String projectTimestamp = (String) item.getOptions().get("projectTimestamp");
        String reportUrl = getReportURL(instanceUrl,"dashboard/index/", projectTimestamp);
        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();
        return new DataResponse<>(result, lastExecuted, reportUrl);
    }


    protected ObjectId getRequestId(BlackDuckRequest request) {
        return request.getId();
    }

    protected CollectorType getCollectorType() {
        return CollectorType.BlackDuck;
    }

    protected DataResponse<Iterable<BlackDuck>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
    }

    protected Component getComponent(ObjectId id) {
        return componentRepository.findOne(id);
    }
}
