package com.capitalone.dashboard.model;


/**
 * Enumerates the possible {@link com.capitalone.dashboard.model.BlackDuck} types.
 */

public enum  BlackDuckType {
    BlackDuck(CollectorType.BlackDuck);

    private final CollectorType collectorType;

    BlackDuckType(CollectorType collectorType) {this.collectorType = collectorType; }

    public static BlackDuckType fromString(String value) {
        for (BlackDuckType securityType : values()) {
            if (securityType.toString().equalsIgnoreCase(value)) {
                return securityType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid BlackDuckType.");
    }

    public CollectorType collectorType() {
        return collectorType;
    }
}