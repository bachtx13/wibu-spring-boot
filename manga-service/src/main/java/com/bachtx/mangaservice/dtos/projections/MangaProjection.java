package com.bachtx.mangaservice.dtos.projections;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MangaProjection {
    public MangaProjection(
            UUID id,
            Object authors
    ) {
        System.out.println(authors);
    }

    private UUID id;

    private String title;

    private String thumbnailUrl;

    private String description;

    private Long views;

    private PublisherInfo publisher;

    private List<AuthorInfo> authors;

    private List<GenreInfo> genres;

    public class PublisherInfo {
        Long id;

        String username;
    }

    public class AuthorInfo {
        Long id;

        String name;
    }

    public class GenreInfo {
        Long id;

        String title;
    }
}
