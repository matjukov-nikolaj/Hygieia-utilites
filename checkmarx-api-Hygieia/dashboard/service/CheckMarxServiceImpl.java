package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.CheckMarxRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.CheckMarxRequest;
import codesecurity.api.service.CodeSecurityService;
import codesecurity.collectors.model.CodeSecurityMetrics;
import com.google.common.collect.Iterables;
import com.mysema.query.BooleanBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Iterable<CheckMarx> CheckMarxDataList = checkMarxRepository.findAll(builder.getValue(), security.timestamp.desc());
        CheckMarx currentCheckMarxData = Iterables.toArray(CheckMarxDataList, CheckMarx.class)[0];
        List<CheckMarx> result = new ArrayList<>();
        result.add(currentCheckMarxData);

        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();
        String reportUrl = "";
        CheckMarx resultOfComparison = null;
        if ((Boolean) item.getOptions().get("current")) {
            reportUrl = (String) item.getOptions().get("instanceUrl");
            resultOfComparison = getResultOfComparisonBetweenCurrAndPrevData(currentCheckMarxData);
        }
        if (resultOfComparison != null) {
            result.add(resultOfComparison);
        }
        return new DataResponse<>(result, lastExecuted, reportUrl);
    }

    private CheckMarx getResultOfComparisonBetweenCurrAndPrevData(CheckMarx currentData) {
        CheckMarx previousData = getPreviousCheckMarxData(currentData);
        if (previousData == null) {
            return null;
        }
        CheckMarx resultOfComparison = new CheckMarx();
        Map<String, Integer>  difference = getDifferenceBetweenTheCurrAndPrevData(currentData, previousData);
        resultOfComparison.setMetrics(difference);
        return resultOfComparison;
    }

    private CheckMarx getPreviousCheckMarxData(CheckMarx result) {
        String projectName = result.getName();
        List<CheckMarx> checkMarxDataByCurrentProjectName = checkMarxRepository.findByProjectName(projectName);
        checkMarxDataByCurrentProjectName.removeIf(p -> (p.getTimestamp() == result.getTimestamp()));
        if (checkMarxDataByCurrentProjectName.isEmpty()) {
            return null;
        }
        return Collections.max(checkMarxDataByCurrentProjectName, Comparator.comparing(object -> object.getTimestamp()));
    }

    private Map<String, Integer> getDifferenceBetweenTheCurrAndPrevData(CheckMarx current, CheckMarx previous) {
        Map<String, Integer> difference = new HashMap<>();
        difference.put(CodeSecurityMetrics.LOW, getDifferenceOfMetrics(current, previous, CodeSecurityMetrics.LOW));
        difference.put(CodeSecurityMetrics.MEDIUM, getDifferenceOfMetrics(current, previous, CodeSecurityMetrics.MEDIUM));
        difference.put(CodeSecurityMetrics.HIGH, getDifferenceOfMetrics(current, previous, CodeSecurityMetrics.HIGH));
        difference.put(CodeSecurityMetrics.TOTAL, getDifferenceOfMetrics(current, previous, CodeSecurityMetrics.TOTAL));
        return difference;
    }

    private Integer getDifferenceOfMetrics(CheckMarx current, CheckMarx previous, String metricField) {
        return previous.getMetrics().get(metricField) - current.getMetrics().get(metricField);
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
