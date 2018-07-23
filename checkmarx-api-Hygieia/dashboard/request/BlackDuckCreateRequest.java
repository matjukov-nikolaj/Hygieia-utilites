package com.capitalone.dashboard.request;

import javax.validation.constraints.NotNull;
import codesecurity.api.request.CodeSecurityCreateRequest;

public class BlackDuckCreateRequest extends CodeSecurityCreateRequest {
    @NotNull
    private String projectTimestamp;

    public String getProjectTimestamp() {
        return projectTimestamp;
    }

    public void setProjectTimestamp(String projectTimestamp) {
        this.projectTimestamp = projectTimestamp;
    }
}
