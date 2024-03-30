package org.bachtx.wibuspringboot.controllers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("example")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ExampleController {
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Hello";
    }
}
