package com.bachtx.wibucommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERecordStatus {
    DISABLED("disabled"),
    ENABLED("enabled"),
    IGNORE_STATUS("ignore_status");
    private final String status;
}
