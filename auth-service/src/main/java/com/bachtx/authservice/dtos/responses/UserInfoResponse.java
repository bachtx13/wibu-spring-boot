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
    private List<EUserRole> roles;
    private Instant createdDate;
    private Instant lastUpdated;
}
