package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;

import java.util.List;
import java.util.UUID;

public interface IGenreService {
    GenreResponse create(UpdateGenrePayload payload);
    GenreResponse update(UUID id, UpdateGenrePayload payload);
    GenreResponse updateStatus(UUID id, boolean status);
    GenreResponse getById(UUID id);
    List<GenreResponse> getAll();
}
