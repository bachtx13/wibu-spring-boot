package com.bachtx.mangaservice.entities;

import com.bachtx.wibucommon.entities.EntityTemplate;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Chapter extends EntityTemplate {
    private String title;
    private String thumbnailUrl;
    private Long views = 0L;
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StoryPage> pages;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    public void setPages(List<StoryPage> pages) {
        pages.forEach(p -> p.setChapter(this));
        this.pages = pages;
    }
}
