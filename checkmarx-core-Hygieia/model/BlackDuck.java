package com.capitalone.dashboard.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection="blackduck")
public class BlackDuck extends CodeSecurity<BlackDuckType> {

    private Map<String, String> metrics;

    public Map<String, String> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, String> metrics) {
        this.metrics = metrics;
    }


}
