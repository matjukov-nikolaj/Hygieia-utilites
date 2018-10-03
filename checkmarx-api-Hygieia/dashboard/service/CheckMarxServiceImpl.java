package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.CheckMarxRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.CheckMarxRequest;
import com.google.common.collect.Iterables;
import com.mysema.query.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckMarxServiceImpl implements CheckMarxService {
    private final CheckMarxRepository checkMarxRepository;
    private final CollectorRepository collectorRepository;

    private CheckMarxServiceController serviceController;

    @Autowired
    public CheckMarxServiceImpl(CheckMarxRepository checkMarxRepository,
                                ComponentRepository componentRepository,
                                CollectorRepository collectorRepository) {
        this.checkMarxRepository = checkMarxRepository;
        this.collectorRepository = collectorRepository;

        this.serviceController = new CheckMarxServiceController(checkMarxRepository, componentRepository);
    }

    public DataResponse<Iterable<CheckMarx>> search(CheckMarxRequest request) {
        if (request == null && request.getType() == null) {
            return this.serviceController.emptyResponse();
        }
        return searchType(request);
    }

    public DataResponse<Iterable<CheckMarx>> searchType(CheckMarxRequest request) {
        CollectorItem item = this.serviceController.getCollectorItem(request);
        if (item == null) {
            return this.serviceController.emptyResponse();
        }

        QCheckMarx security = new QCheckMarx("checkmarx");
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(security.collectorItemId.eq(item.getId()));
        Iterable<CheckMarx> checkMarxDataList = checkMarxRepository.findAll(builder.getValue(), security.timestamp.desc());
        CheckMarx currentCheckMarxData = Iterables.toArray(checkMarxDataList, CheckMarx.class)[0];
        List<CheckMarx> result = new ArrayList<>();
        result.add(currentCheckMarxData);

        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();
        String reportUrl = "";
        CheckMarx resultOfComparison = null;
        if ((Boolean) item.getOptions().get("current")) {
            reportUrl = (String) item.getOptions().get("instanceUrl");
            resultOfComparison = this.serviceController.getResultOfComparisonBetweenCurrAndPrevData(currentCheckMarxData);
        }
        if (resultOfComparison != null) {
            result.add(resultOfComparison);
        }
        return new DataResponse<>(result, lastExecuted, reportUrl);
    }
}
