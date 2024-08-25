package com.bachtx.wibucommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ESortType {
    ASC("asc"),
    DESC("desc");
    private final String type;
}
