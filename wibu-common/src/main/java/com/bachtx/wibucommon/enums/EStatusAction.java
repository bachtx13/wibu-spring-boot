package com.bachtx.wibucommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EStatusAction {
    ACTIVE("active"),
    DE_ACTIVE("deActive");
    private final String action;
}
