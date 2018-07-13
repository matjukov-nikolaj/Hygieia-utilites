package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.CheckMarxRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.CheckMarxRequest;
import com.google.common.collect.Iterables;
import com.google.common.base.Objects;
import com.mysema.query.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckMarxServiceImpl implements CheckMarxService {
    private final CheckMarxRepository checkMarxRepository;
    private final ComponentRepository componentRepository;
    private final CollectorRepository collectorRepository;
    private final CollectorService collectorService;

    @Autowired
    public CheckMarxServiceImpl(CheckMarxRepository checkMarxRepository,
                                ComponentRepository componentRepository,
                                CollectorRepository collectorRepository,
                                CollectorService collectorService) {
        this.checkMarxRepository = checkMarxRepository;
        this.componentRepository = componentRepository;
        this.collectorRepository = collectorRepository;
        this.collectorService = collectorService;
    }

    @Override
    public DataResponse<Iterable<CheckMarx>> search(CheckMarxRequest request) {
        if (request == null && request.getType() == null) {
            return emptyResponse();
        }
        return searchType(request);
    }

    protected DataResponse<Iterable<CheckMarx>> emptyResponse() {
        return new DataResponse<>(null, System.currentTimeMillis());
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

    protected CollectorItem getCollectorItem(CheckMarxRequest request) {
        CollectorItem item = null;
        Component component = componentRepository.findOne(request.getId());
        CheckMarxType checkMarxType = Objects.firstNonNull(request.getType(),
                CheckMarxType.CheckMarx);
        List<CollectorItem> items = component.getCollectorItems().get(CollectorType.CheckMarx);
        if (items != null) {
            item = Iterables.getFirst(items, null);
        }
        return item;
    }

    private String getReportURL(String projectUrl,String path, String projectId) {
        StringBuilder sb = new StringBuilder(projectUrl);
        if(!projectUrl.endsWith("/")) {
            sb.append("/");
        }
        sb.append(path)
                .append(projectId);
        return sb.toString();
    }

}
