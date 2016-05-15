package org.impress.storage.exceptions

enum ResponseErrorCode {
    VALIDATION_ERROR,
    AREA_NOT_FOUND,
    VARIABLE_NOT_FOUND,
    DEVICE_NOT_FOUND,
    GROUP_NOT_FOUND,
    SIMULATION_NOT_FOUND,
    DUPLICATES_FOUND,
    INVALID_TIMESTAMP,
    INVALID_GRANULARITY,
    AUTHENTICATION_ERROR,
    MEASUREMENT_NOT_FOUND,
    INVALID_MEASUREMENT_VARIABLE,
    INVALID_MEASUREMENT_VALUE,
    NOT_IMPLEMENTED
}