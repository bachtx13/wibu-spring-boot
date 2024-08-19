package com.bachtx.mangaservice.dtos.payloads;

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
public class CreateStoryPagePayload {
    private String imageUrl;
}
