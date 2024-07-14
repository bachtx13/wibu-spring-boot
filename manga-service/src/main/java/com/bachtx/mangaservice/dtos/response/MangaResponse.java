package com.bachtx.mangaservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MangaResponse {
    private UUID id;
    private String title;
    private String thumbnailUrl;
    private String description;
    private UserInfoResponse publisher;
    private List<AuthorResponse> authors;
    private List<GenreResponse> genres;
    private List<ChapterResponse> chapters;
    private boolean disabled;
    private Instant createdDate;
    private Instant lastUpdated;
}
