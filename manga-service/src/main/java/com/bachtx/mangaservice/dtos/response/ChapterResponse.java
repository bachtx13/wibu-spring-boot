package com.bachtx.mangaservice.dtos.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChapterResponse {
    private UUID id;
    private String title;
    private String thumbnailUrl;
    private MangaResponse manga;
    private List<StoryPageResponse> pages;
}
