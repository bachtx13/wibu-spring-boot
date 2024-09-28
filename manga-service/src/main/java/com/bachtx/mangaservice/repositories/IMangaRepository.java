package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMangaRepository extends _IBaseJpaRepository<Manga, UUID> {
    Long countByDisabled(boolean disabled);

    List<Manga> findAll();

    Page<Manga> findAll(Pageable pageable);

    Page<Manga> findAll(Specification<Manga> specification, Pageable pageable);
}
