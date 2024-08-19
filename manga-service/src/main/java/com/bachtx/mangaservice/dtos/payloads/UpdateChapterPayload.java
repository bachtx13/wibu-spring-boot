package com.bachtx.mangaservice.dtos.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UpdateChapterPayload {
    private String title;
    private String thumbnailUrl;
    private List<CreateStoryPagePayload> pages;
}
