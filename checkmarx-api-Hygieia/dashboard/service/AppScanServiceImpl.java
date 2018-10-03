package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.*;
import com.capitalone.dashboard.repository.AppScanRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.request.AppScanRequest;
import codesecurity.api.service.CodeSecurityService;
import com.google.common.collect.Iterables;
import com.mysema.query.BooleanBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppScanServiceImpl implements AppScanService {

    private static final Log LOG = LogFactory.getLog(CodeSecurityService.class);

    private final AppScanRepository appScanRepository;
    private final CollectorRepository collectorRepository;
    private AppScanServiceController appScanServiceController;

    @Autowired
    public AppScanServiceImpl(AppScanRepository appScanRepository,
                                CollectorRepository collectorRepository,
                                ComponentRepository componentRepository) {
        this.appScanRepository = appScanRepository;
        this.collectorRepository = collectorRepository;

        this.appScanServiceController = new AppScanServiceController(appScanRepository, componentRepository);
    }

    @Override
    public DataResponse<Iterable<AppScan>> search(AppScanRequest request) {
        try {
            if (request == null && request.getType() == null) {
                return this.appScanServiceController.emptyResponse();
            }
            return searchType(request);
        } catch (Exception e) {
            LOG.error(e);
            return this.appScanServiceController.emptyResponse();
        }
    }

    private DataResponse<Iterable<AppScan>> searchType(AppScanRequest request) {
        CollectorItem item = this.appScanServiceController.getCollectorItem(request);
        if (item == null) {
            return this.appScanServiceController.emptyResponse();
        }

        QAppScan qAppScan = new QAppScan("appscan");
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAppScan.collectorItemId.eq(item.getId()));

        Iterable<AppScan> appScanDataList = appScanRepository.findAll(builder.getValue(), qAppScan.timestamp.desc());
        AppScan currentAppScanData = Iterables.toArray(appScanDataList, AppScan.class)[0];
        List<AppScan> result = new ArrayList<>();
        result.add(currentAppScanData);

        Collector collector = collectorRepository.findOne(item.getCollectorId());
        long lastExecuted = (collector == null) ? 0 : collector.getLastExecuted();

        String reportUrl = "";
        AppScan resultOfComparison = null;
        if ((Boolean) item.getOptions().get("current")) {
            reportUrl = (String) item.getOptions().get("instanceUrl");
            resultOfComparison = this.appScanServiceController.getResultOfComparisonBetweenCurrAndPrevData(currentAppScanData);
        }
        if (resultOfComparison != null) {
            result.add(resultOfComparison);
        }

        return new DataResponse<>(result, lastExecuted, reportUrl);
    }
}
