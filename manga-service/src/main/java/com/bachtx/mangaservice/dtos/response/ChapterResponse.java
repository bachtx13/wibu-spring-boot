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
public class ChapterResponse {
    private UUID id;
    private String title;
    private String thumbnailUrl;
    private List<StoryPageResponse> pages;
    private Integer views;
    private Instant createdDate;
    private Instant lastUpdated;
}
