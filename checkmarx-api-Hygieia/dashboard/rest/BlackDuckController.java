package com.capitalone.dashboard.rest;

import com.capitalone.dashboard.model.BlackDuck;
import com.capitalone.dashboard.model.BlackDuckType;
import com.capitalone.dashboard.model.DataResponse;
import com.capitalone.dashboard.request.BlackDuckRequest;
import com.capitalone.dashboard.service.BlackDuckServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BlackDuckController
{
    private final BlackDuckServiceImpl blackDuckService;

    @Autowired
    public BlackDuckController(BlackDuckServiceImpl blackDuckService) {
        this.blackDuckService = blackDuckService;
    }

    @RequestMapping(value = "/blackduck", method = GET, produces = APPLICATION_JSON_VALUE)
    public DataResponse<Iterable<BlackDuck>> blackDuckData(@Valid BlackDuckRequest request) {
        request.setType(BlackDuckType.BlackDuck);
        return blackDuckService.search(request);
    }
}
