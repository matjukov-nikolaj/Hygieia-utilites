package com.capitalone.dashboard.service;

import codesecurity.api.service.CodeSecurityService;
import codesecurity.config.Constants;
import com.capitalone.dashboard.model.BlackDuck;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Component;
import com.capitalone.dashboard.repository.BlackDuckRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.BlackDuckRequest;
import org.bson.types.ObjectId;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackDuckServiceController extends CodeSecurityService<BlackDuck, BlackDuckRequest> {
    private static final String DECIMAL_FORMAT_PATTERN = "#0.00";

    private final BlackDuckRepository blackDuckRepository;
    private final ComponentRepository componentRepository;

    public BlackDuckServiceController(BlackDuckRepository blackDuckRepository,
                                    ComponentRepository componentRepository) {
        this.blackDuckRepository = blackDuckRepository;
        this.componentRepository = componentRepository;
    }

    public Map<String, Double> getPercentagesBetweenCurrAndPrevData(BlackDuck previous, BlackDuck current) {
        Map<String, Double> resultBetweenPercents = new HashMap<>();
        resultBetweenPercents.put(getASecondFiledFromMap(), getDifferenceOfPercents(previous, current, getASecondFiledFromMap()));
        resultBetweenPercents.put(getAThirdFieldFromMap(), getDifferenceOfPercents(previous, current, getAThirdFieldFromMap()));
        return resultBetweenPercents;
    }

    private Double getDifferenceOfPercents(BlackDuck previous, BlackDuck current, String percentField) {
        Double firstValue = getPercentField(previous, percentField);
        Double secondValue = getPercentField(current, percentField);
        Double result = Math.abs(firstValue - secondValue);
        DecimalFormat formatConverter = new DecimalFormat(DECIMAL_FORMAT_PATTERN);
        String formatedResult = formatConverter.format(result);
        return Double.parseDouble(formatedResult.replace(",", "."));
    }

    protected Double getPercentField(BlackDuck object, String metricField) { return object.getPercentages().get(metricField); }

    protected String getProjectNameFromObject(BlackDuck object) { return object.getName(); }

    protected String getAFirstFieldFromMap() { return Constants.BlackDuck.NUMBER_OF_FILES; }

    protected String getASecondFiledFromMap() { return Constants.BlackDuck.FILES_PENDING_IDENTIFICATION; }

    protected String getAThirdFieldFromMap() { return Constants.BlackDuck.FILES_WITH_VIOLATIONS; }

    protected List<BlackDuck> getDataByCurrentProjectName(String projectName) {
        return blackDuckRepository.findByProjectName(projectName);
    }

    protected BlackDuck getResultOfComparison(Map<String, Integer> difference) {
        BlackDuck result = new BlackDuck();
        result.setMetrics(difference);
        return result;
    }

    protected Long getProjectTimestamp(BlackDuck object) { return object.getTimestamp(); }

    protected Integer getMetricField(BlackDuck object, String metricField) { return object.getMetrics().get(metricField); }

    protected ObjectId getRequestId(BlackDuckRequest request) {
        return request.getId();
    }

    protected CollectorType getCollectorType() {
        return CollectorType.BlackDuck;
    }

    protected Component getComponent(ObjectId id) {
        return componentRepository.findOne(id);
    }

}
