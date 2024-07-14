package com.bachtx.mangaservice.repositories;

import com.bachtx.mangaservice.entities.StoryPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IStoryPageRepository extends JpaRepository<StoryPage, UUID> {
}
