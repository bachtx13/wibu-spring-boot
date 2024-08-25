package com.bachtx.mangaservice.dtos.raws;

import com.bachtx.mangaservice.entities.Manga;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MangaPreviewRaw {
    private Manga manga;
    private long totalViews;

    public MangaPreviewRaw(Manga manga, long totalViews) {
        this.manga = manga;
        this.totalViews = totalViews;
    }
}
