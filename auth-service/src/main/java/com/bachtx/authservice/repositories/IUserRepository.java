package com.bachtx.authservice.repositories;

import com.bachtx.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
