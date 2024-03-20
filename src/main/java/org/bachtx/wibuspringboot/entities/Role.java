package org.bachtx.wibuspringboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -4482693934040101521L;
    private String name;
}
