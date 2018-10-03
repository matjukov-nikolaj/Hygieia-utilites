package com.capitalone.dashboard.rest;

import com.capitalone.dashboard.model.AppScan;
import com.capitalone.dashboard.model.AppScanType;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.AppScanRequest;
import com.capitalone.dashboard.service.AppScanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class AppScanController
{
    private final AppScanServiceImpl appScanService;

    @Autowired
    public AppScanController(AppScanServiceImpl appScanService) {
        this.appScanService = appScanService;
    }

    @RequestMapping(value = "/appscan", method = GET, produces = APPLICATION_JSON_VALUE)
    public DataResponse<Iterable<AppScan>> blackDuckData(@Valid AppScanRequest request) {
        request.setType(AppScanType.AppScan);
        return appScanService.search(request);
    }
}
