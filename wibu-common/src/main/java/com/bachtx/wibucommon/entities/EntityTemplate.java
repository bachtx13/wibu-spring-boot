package com.bachtx.wibucommon.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(of = {"id"})
public class EntityTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(columnDefinition = "boolean default false")
    private boolean disabled = false;
    private Instant createdDate;
    private Instant lastUpdated;

    @PrePersist
    private void prePersist() {
        this.createdDate = Instant.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.lastUpdated = Instant.now();
    }
}
