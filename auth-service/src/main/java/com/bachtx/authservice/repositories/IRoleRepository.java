package com.bachtx.authservice.repositories;

import com.bachtx.authservice.entities.Role;
import com.bachtx.authservice.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(EUserRole roleName);
}
