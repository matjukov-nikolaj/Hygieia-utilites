package com.capitalone.dashboard.model;

/**
 * Enumerates the possible {@link AppScan} types.
 */

public enum  AppScanType {
    AppScan(CollectorType.AppScan);

    private final CollectorType collectorType;

    AppScanType(CollectorType collectorType) {this.collectorType = collectorType; }

    public static AppScanType fromString(String value) {
        for (AppScanType securityType : values()) {
            if (securityType.toString().equalsIgnoreCase(value)) {
                return securityType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid AppScanType.");
    }

    public CollectorType collectorType() {
        return collectorType;
    }
}