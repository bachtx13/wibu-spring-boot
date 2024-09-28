package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.entities.Manga;

public interface IMangaService extends _IBaseCRUDService<Manga, MangaResponse, UpdateMangaPayload, UpdateMangaPayload> {
}
