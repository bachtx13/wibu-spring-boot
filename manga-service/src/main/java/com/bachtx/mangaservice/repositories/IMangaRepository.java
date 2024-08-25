package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.dtos.raws.MangaPreviewRaw;
import com.bachtx.mangaservice.entities.Manga;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMangaRepository extends JpaRepository<Manga, UUID> {
    @Query("SELECT new com.bachtx.mangaservice.dtos.raws.MangaPreviewRaw(m, SUM(coalesce(c.views, 0) )) " +
        "FROM Manga m " +
        "LEFT JOIN m.chapters c " +
        "GROUP BY m.id")
    List<MangaPreviewRaw> findAllPreviewManga(Pageable pageable);
}
