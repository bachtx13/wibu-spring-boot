package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    @Nullable
    User findByEmail(String email);
}
