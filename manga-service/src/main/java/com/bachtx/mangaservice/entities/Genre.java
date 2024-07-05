package com.bachtx.mangaservice.entities;

import com.bachtx.wibucommon.entities.EntityTemplate;
import jakarta.persistence.Column;
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
public class Genre extends EntityTemplate {
    @Column(nullable = false, unique = true)
    private String title;
    private String description;
    private String thumbnailUrl;
    @ManyToMany(mappedBy = "genres")
    private List<Manga> mangas;
}
