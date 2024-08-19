package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;

public interface IGenreService extends IBaseCRUDService<GenreResponse, UpdateGenrePayload, UpdateGenrePayload> {
}