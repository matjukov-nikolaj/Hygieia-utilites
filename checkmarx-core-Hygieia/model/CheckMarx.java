package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection="checkmarx")
public class CheckMarx extends CodeSecurity<CheckMarxType> {
    private Map<String, Integer> metrics;

    public Map<String, Integer> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Integer> metrics) {
        this.metrics = metrics;
    }
}
