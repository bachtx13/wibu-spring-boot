package com.bachtx.authservice.bootstrap;

import com.bachtx.authservice.services.IRoleService;
import com.bachtx.authservice.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleInitCommandLineRunner implements CommandLineRunner {
    private final IRoleService roleService;
    private final IUserService userService;

    @Override
    public void run(String... args) {
        roleService.init();
        userService.initAdminUser();
    }
}
