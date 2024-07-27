package com.bachtx.mangaservice.dtos.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
@AllArgsConstructor
@Getter
@Setter
public class UpdateGenrePayload {
    @NotEmpty
    private String title;
    private String thumbnailUrl;
}
