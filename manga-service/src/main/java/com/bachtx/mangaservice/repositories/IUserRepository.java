package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.User;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface IUserRepository extends _IBaseJpaRepository<User, UUID> {
    @Nullable
    User findByEmail(String email);
}
