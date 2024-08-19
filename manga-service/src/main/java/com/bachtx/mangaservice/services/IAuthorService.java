package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;

public interface IAuthorService extends IBaseCRUDService<AuthorResponse, UpdateAuthorPayload, UpdateAuthorPayload> {
}
