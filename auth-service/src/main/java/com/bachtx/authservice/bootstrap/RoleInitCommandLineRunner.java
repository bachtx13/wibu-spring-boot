package com.bachtx.authservice.bootstrap;

import com.bachtx.authservice.services.impl.RoleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleInitCommandLineRunner implements CommandLineRunner {
    private final RoleServiceImpl roleService;

    @Override
    public void run(String... args) {
        roleService.init();
    }
}
