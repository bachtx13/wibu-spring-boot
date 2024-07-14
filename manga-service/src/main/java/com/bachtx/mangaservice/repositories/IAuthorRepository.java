package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findAllByIdIn(List<UUID> ids);
}
