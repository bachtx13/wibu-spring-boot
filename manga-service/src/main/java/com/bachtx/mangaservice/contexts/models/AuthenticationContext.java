package com.bachtx.mangaservice.contexts.models;

import com.bachtx.mangaservice.entities.Role;
import com.bachtx.mangaservice.entities.User;
import com.bachtx.wibucommon.enums.EUserRole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationContext {
    private User principal;
    private List<Role> authorities;
    private boolean authenticated;
    private String cause;

    public boolean hasRole(EUserRole role) {
        return authorities.stream()
                .map(Role::getName)
                .toList()
                .contains(role);
    }
}
