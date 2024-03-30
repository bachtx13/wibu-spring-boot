package org.bachtx.wibuspringboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EUserRole {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER"), ROLE_ANONYMOUS("ROLE_ANONYMOUS");
    private final String roleName;
}
