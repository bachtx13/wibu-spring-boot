package com.bachtx.mangaservice.dtos.payloads;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class CreateChapterPayload extends UpdateChapterPayload {
    private UUID mangaId;
}
