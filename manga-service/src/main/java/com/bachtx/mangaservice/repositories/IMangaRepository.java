package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMangaRepository extends JpaRepository<Manga, UUID> {
    Long countByDisabled(boolean disabled);
}
