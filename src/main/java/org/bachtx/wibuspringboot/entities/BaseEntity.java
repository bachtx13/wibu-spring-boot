package org.bachtx.wibuspringboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(of = {"id"})
public abstract class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -8868877885520192939L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private boolean isDisable;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
