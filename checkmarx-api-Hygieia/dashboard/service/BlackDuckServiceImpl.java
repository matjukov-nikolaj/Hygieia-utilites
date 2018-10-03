package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.BlackDuckRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.BlackDuckRequest;
import com.google.common.collect.Iterables;
import com.mysema.query.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlackDuckServiceImpl implements BlackDuckService {

    private final BlackDuckRepository blackDuckRepository;
    private final CollectorRepository collectorRepository;
    private BlackDuck resultOfComparison = null;
    private BlackDuckServiceController serviceController;


    @Autowired
    public BlackDuckServiceImpl(BlackDuckRepository blackDuckRepository,
                               CollectorRepository collectorRepository,
                                ComponentRepository componentRepository) {
        this.blackDuckRepository = blackDuckRepository;
        this.collectorRepository = collectorRepository;
        this.serviceController = new BlackDuckServiceController(blackDuckRepository, componentRepository);
    }

    public DataResponse<Iterable<BlackDuck>> search(BlackDuckRequest request) {
        if (request == null && request.getType() == null) {
            return this.serviceController.emptyResponse();
        }
        return searchType(request);
    }

    public DataResponse<Iterable<BlackDuck>> searchType(BlackDuckRequest request) {
        CollectorItem item = this.serviceController.getCollectorItem(request);
        if (item == null) {
            return this.serviceController.emptyResponse();
        }

        QBlackDuck qBlackDuck = new QBlackDuck("blackduck");
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qBlackDuck.collectorItemId.eq(item.getId()));
        Iterable<BlackDuck> blackDuckDataList = blackDuckRepository.findAll(builder.getValue(), qBlackDuck.timestamp.desc());
        BlackDuck currentBlackDuckData = Iterables.toArray(blackDuckDataList, BlackDuck.class)[0];
        List<BlackDuck> result = new ArrayList<>();
        result.add(currentBlackDuckData);

        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();
        String reportUrl = "";
        if ((Boolean) item.getOptions().get("current")) {
            reportUrl = (String) item.getOptions().get("instanceUrl");
            this.resultOfComparison = this.serviceController.getResultOfComparisonBetweenCurrAndPrevData(currentBlackDuckData);
        }
        if (this.resultOfComparison != null) {
            BlackDuck previousData = this.serviceController.getPreviousData();
            this.resultOfComparison.setPercentages(this.serviceController.getPercentagesBetweenCurrAndPrevData(previousData, currentBlackDuckData));
            result.add(this.resultOfComparison);
        }

        return new DataResponse<>(result, lastExecuted, reportUrl);
    }
}
