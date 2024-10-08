package com.bachtx.authservice.entities;

import com.bachtx.wibucommon.entities.EntityTemplate;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "user_credential")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class User extends EntityTemplate {
    @Column(unique = true, nullable = false)
    private String email;
    private String username;
    private String avatarUrl;
    @Column(nullable = false)
    private Instant birthday;
    @Column(unique = true, nullable = false)
    private String password;
    private boolean verified;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TokenHolder tokenHolder;
}
