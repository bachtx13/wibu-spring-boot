package com.bachtx.mangaservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreResponse {
    private UUID id;
    private String title;
    private String thumbnailUrl;
    private String description;
    private boolean disabled;
    private Instant createdDate;
    private Instant lastUpdated;
}
