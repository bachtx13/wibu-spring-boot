package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, UUID> {
    List<Genre> findAllByIdIn(List<UUID> ids);
    List<Genre> findAll();
    Page<Genre> findAll(Pageable pageable);
    Page<Genre> findAll(Specification<Genre> specification, Pageable pageable);
}
