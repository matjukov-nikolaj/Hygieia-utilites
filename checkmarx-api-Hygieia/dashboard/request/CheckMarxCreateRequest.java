package com.capitalone.dashboard.request;

import javax.validation.constraints.NotNull;
import codesecurity.api.request.CodeSecurityCreateRequest;

public class CheckMarxCreateRequest extends CodeSecurityCreateRequest {
    @NotNull
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
