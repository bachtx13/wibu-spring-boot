package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;

import java.util.List;
import java.util.UUID;

public interface IMangaService {
    MangaResponse create(UpdateMangaPayload payload);
    MangaResponse update(UUID id, UpdateMangaPayload payload);
    MangaResponse updateStatus(UUID id, boolean status);
    MangaResponse getById(UUID id);
    List<MangaResponse> getAll();
}
