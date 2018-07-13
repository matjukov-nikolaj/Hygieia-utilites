package com.capitalone.dashboard.request;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class CheckMarxCreateRequest {
    private String hygieiaId;
    @NotNull
    private long timestamp;
    @NotNull
    private String projectName;
    @NotNull
    private String projectId;
    @NotNull
    private String projectUrl;
    @NotNull
    private String serverUrl;
    @NotNull
    private Map<String, Integer> metrics = new HashMap<>();

    public String getHygieiaId() {
        return hygieiaId;
    }

    public void setHygieiaId(String hygieiaId) {
        this.hygieiaId = hygieiaId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Map<String, Integer> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Integer> metrics) { this.metrics = metrics; }

}
