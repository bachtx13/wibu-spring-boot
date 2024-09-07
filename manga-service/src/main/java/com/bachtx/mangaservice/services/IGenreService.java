package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;
import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.wibucommon.services.IBaseCRUDService;

public interface IGenreService extends IBaseCRUDService<Genre, GenreResponse, UpdateGenrePayload, UpdateGenrePayload> {
}