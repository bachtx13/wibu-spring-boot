package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;
import com.bachtx.mangaservice.entities.Genre;

public interface IGenreService extends _IBaseCRUDService<Genre, GenreResponse, UpdateGenrePayload, UpdateGenrePayload> {
}