package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;

import java.util.List;
import java.util.UUID;

public interface IAuthorService {
    AuthorResponse create(UpdateAuthorPayload payload);
    AuthorResponse update(UUID id, UpdateAuthorPayload payload);
    AuthorResponse updateStatus(UUID id, boolean status);
    AuthorResponse getById(UUID id);
    List<AuthorResponse> getAll();
}
