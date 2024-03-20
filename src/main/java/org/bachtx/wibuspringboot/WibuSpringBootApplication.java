package org.bachtx.wibuspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WibuSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WibuSpringBootApplication.class, args);
    }

}
