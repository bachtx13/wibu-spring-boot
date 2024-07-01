package com.bachtx.authservice.controllers;

import com.bachtx.authservice.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("hello-world")
@AllArgsConstructor
@CrossOrigin
public class HelloWorldController {
    private final IUserService userService;

    @GetMapping("")
    public String helloWorld() {
        return "Hello World!";
    }
}
