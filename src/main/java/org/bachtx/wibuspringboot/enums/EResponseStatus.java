package org.bachtx.wibuspringboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EResponseStatus {
    SUCCESS("success"),
    FAIL("fail"),
    ERROR("error");
    private final String status;
}