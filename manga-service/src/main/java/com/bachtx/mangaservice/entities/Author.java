package com.bachtx.mangaservice.entities;

import com.bachtx.wibucommon.entities.EntityTemplate;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Author extends EntityTemplate {
    private String avatarUrl;
    private String name;
    @ManyToMany(mappedBy = "authors")
    private List<Manga> mangas;
    private String description;
}
