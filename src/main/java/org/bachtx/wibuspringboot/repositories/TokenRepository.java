package org.bachtx.wibuspringboot.repositories;

import org.bachtx.wibuspringboot.entities.TokenEntity;
import org.bachtx.wibuspringboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
    TokenEntity findByUser(User user);

    TokenEntity findByToken(UUID token);
}
