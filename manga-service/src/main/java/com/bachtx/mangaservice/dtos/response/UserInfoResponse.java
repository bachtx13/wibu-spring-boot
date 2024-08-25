package com.bachtx.mangaservice.dtos.response;

import com.bachtx.wibucommon.enums.EUserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoResponse {
    private String id;
    private String email;
    private String username;
    private List<EUserRole> roles;
    private List<MangaResponse> mangas;
    private Instant createdDate;
    private Instant lastUpdated;
}
