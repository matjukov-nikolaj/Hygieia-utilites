package com.capitalone.dashboard.model;


/**
 * Enumerates the possible {@link CheckMarx} types.
 */

public enum  CheckMarxType {
    CheckMarx(CollectorType.CheckMarx);

    private final CollectorType collectorType;

    CheckMarxType(CollectorType collectorType) {this.collectorType = collectorType; }

    public static CheckMarxType fromString(String value) {
        for (CheckMarxType securityType : values()) {
            if (securityType.toString().equalsIgnoreCase(value)) {
                return securityType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid CheckMarxType.");
    }

    public CollectorType collectorType() {
        return collectorType;
    }
}
