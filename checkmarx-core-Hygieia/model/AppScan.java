package com.capitalone.dashboard.model;

import java.util.Map;

public class AppScan extends CodeSecurity<AppScanType> {

    private Map<String, String> metrics;

    public Map<String, String> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, String> metrics) {
        this.metrics = metrics;
    }

}
