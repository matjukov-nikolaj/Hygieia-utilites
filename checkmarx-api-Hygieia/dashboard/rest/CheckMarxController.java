package com.capitalone.dashboard.rest;

import com.capitalone.dashboard.editors.CaseInsensitiveCheckMarxTypeEditor;
import com.capitalone.dashboard.model.CheckMarx;
import com.capitalone.dashboard.model.CheckMarxType;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.CheckMarxRequest;
import com.capitalone.dashboard.service.CheckMarxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class CheckMarxController
{
    private final CheckMarxServiceImpl checkMarxService;

    @Autowired
    public CheckMarxController(CheckMarxServiceImpl checkMarxService) {
        this.checkMarxService = checkMarxService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CheckMarxType.class, new CaseInsensitiveCheckMarxTypeEditor());
    }

    @RequestMapping(value = "/checkmarx", method = GET, produces = APPLICATION_JSON_VALUE)
    public DataResponse<Iterable<CheckMarx>> checkMarxData(@Valid CheckMarxRequest request) {
        request.setType(CheckMarxType.CheckMarx);
        return checkMarxService.search(request);
    }
}
