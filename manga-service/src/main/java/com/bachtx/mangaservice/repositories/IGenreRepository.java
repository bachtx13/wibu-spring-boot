package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, UUID> {
    List<Genre> findAllByIdIn(List<UUID> ids);
}
