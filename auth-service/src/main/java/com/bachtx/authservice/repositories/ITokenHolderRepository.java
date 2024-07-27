package com.bachtx.authservice.repositories;

import com.bachtx.authservice.entities.TokenHolder;
import com.bachtx.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITokenHolderRepository extends JpaRepository<TokenHolder, UUID> {
    TokenHolder findByUser(User user);

    TokenHolder findByToken(UUID token);
}
