package org.bachtx.wibuspringboot.services;

import org.bachtx.wibuspringboot.entities.User;

public interface UserService {
    User findByEmail(String email);
}
