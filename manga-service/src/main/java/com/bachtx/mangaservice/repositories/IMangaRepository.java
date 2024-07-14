package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IMangaRepository extends JpaRepository<Manga, UUID> {
    @Query("select m from Manga m " +
            "join fetch m.authors " +
            "join fetch m.genres " +
            "join fetch m.publisher " +
            "where m.id = :mangaId")
    Optional<Manga> findFullById(@Param("mangaId") UUID id);
}
