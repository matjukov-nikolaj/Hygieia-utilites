package com.capitalone.dashboard.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection="blackduck")
public class BlackDuck extends BaseModel {

    private ObjectId collectorItemId;
    private long timestamp;

    private String projectName;
    private String url;
    private ObjectId buildId;
    private Map<String, Integer> metrics;

    private Map<String, Double> percentages;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return projectName;
    }

    public void setName(String name) {
        this.projectName = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public ObjectId getBuildId() {
        return buildId;
    }

    public void setBuildId(ObjectId buildId) {
        this.buildId = buildId;
    }

    public Map<String, Integer> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Integer> metrics) {
        this.metrics = metrics;
    }

    public Map<String, Double> getPercentages() {
        return percentages;
    }

    public void setPercentages(Map<String, Double> percentages) {
        this.percentages = percentages;
    }

}
