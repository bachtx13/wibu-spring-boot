package com.bachtx.authservice.services.impl;

import com.bachtx.authservice.entities.Role;
import com.bachtx.authservice.repositories.IRoleRepository;
import com.bachtx.authservice.services.IRoleService;
import com.bachtx.wibucommon.enums.EUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    @Override
    public void init() {
        if (count() == 0) {
            Set<Role> roles = Arrays.stream(EUserRole.values())
                    .map(r -> Role.builder()
                            .name(r)
                            .build())
                    .collect(Collectors.toSet());
            roleRepository.saveAll(roles);
        }
    }

    private Long count() {
        return roleRepository.count();
    }
}
