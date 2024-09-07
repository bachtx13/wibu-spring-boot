package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;
import com.bachtx.mangaservice.entities.Author;
import com.bachtx.wibucommon.services.IBaseCRUDService;

public interface IAuthorService extends IBaseCRUDService<Author, AuthorResponse, UpdateAuthorPayload, UpdateAuthorPayload> { }
