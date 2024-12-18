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
public class Manga extends EntityTemplate {
    private String title;
    private String thumbnailUrl;

    private String description;
    private Long views = 0L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private User publisher;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "manga_author",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chapter> chapters;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "manga_genre",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
}
