package com.bachtx.mangaservice.dtos.response;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChapterResponse {
    private UUID id;
    private String name;
    private String avatarUrl;
    private boolean disabled;
    private Instant createdDate;
    private Instant lastUpdated;
}
