package com.bachtx.authservice.dtos.responses;

import com.bachtx.wibucommon.enums.EUserRole;
import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInfoResponse {
    private String id;
    private String email;
    private String username;
    private String avatarUrl;
    private List<EUserRole> roles;
    private Instant birthday;
    private Instant createdDate;
    private Instant lastUpdated;
}
