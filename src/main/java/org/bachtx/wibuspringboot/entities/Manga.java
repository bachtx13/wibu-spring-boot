package org.bachtx.wibuspringboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Manga extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -7729843586101436105L;
    private String title;
    private String author;
    private Set<String> characters;
    private String description;
}
