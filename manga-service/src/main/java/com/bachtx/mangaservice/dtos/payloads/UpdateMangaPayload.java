package com.bachtx.mangaservice.dtos.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
@Builder
@AllArgsConstructor
@Getter
@Setter
public class UpdateMangaPayload {
    @NotEmpty
    private String title;
    @NotEmpty
    private String thumbnailUrl;
    private String description;
    @NotEmpty(message = "Please choose at least one available author")
    private List<UUID> authorIds;
    @NotEmpty(message = "Please choose at least one available genre")
    private List<UUID> genreIds;
}
