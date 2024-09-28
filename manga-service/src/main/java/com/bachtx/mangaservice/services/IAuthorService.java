package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;
import com.bachtx.mangaservice.entities.Author;

public interface IAuthorService extends _IBaseCRUDService<Author, AuthorResponse, UpdateAuthorPayload, UpdateAuthorPayload> {
}
