package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.Chapter;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IChapterRepository extends _IBaseJpaRepository<Chapter, UUID> {
}
