package com.bachtx.authservice.entities;

import com.bachtx.authservice.enums.EUserRole;
import com.bachtx.wibucommon.entities.EntityTemplate;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Role extends EntityTemplate {
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private EUserRole name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
