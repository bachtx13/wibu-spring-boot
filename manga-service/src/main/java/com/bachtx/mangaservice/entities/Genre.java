package com.bachtx.mangaservice.entities;

import com.bachtx.wibucommon.entities.EntityTemplate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Genre extends EntityTemplate {
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "title can't be duplicate")
    private String title;
    private String description;
    private String thumbnailUrl;
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Manga> mangas;
}
