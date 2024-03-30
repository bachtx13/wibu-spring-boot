package org.bachtx.wibuspringboot.services.impl;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.entities.Role;
import org.bachtx.wibuspringboot.enums.EUserRole;
import org.bachtx.wibuspringboot.repositories.RoleRepository;
import org.bachtx.wibuspringboot.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void init() {
        if (count() == 0) {
            Set<Role> roles = Arrays.stream(EUserRole.values())
                    .map(r -> Role.builder()
                            .name(r.getRoleName())
                            .build())
                    .collect(Collectors.toSet());
            roleRepository.saveAll(roles);
        }
    }

    private Long count() {
        return roleRepository.count();
    }
}
