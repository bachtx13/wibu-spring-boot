package org.bachtx.wibuspringboot.core.bootstrap;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleInitCommandLineRunner implements CommandLineRunner {
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        roleService.init();
    }
}
