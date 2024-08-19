package com.bachtx.mangaservice.dtos.response;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenreResponse {
    private UUID id;
    private String title;
    private String thumbnailUrl;
    private String description;
    private boolean disabled;
    private Instant createdDate;
    private Instant lastUpdated;
}
