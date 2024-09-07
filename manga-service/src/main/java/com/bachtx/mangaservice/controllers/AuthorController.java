package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;
import com.bachtx.mangaservice.entities.Author;
import com.bachtx.mangaservice.services.IAuthorService;
import com.bachtx.wibucommon.controllers.ACRUDRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController extends ACRUDRestController<Author, AuthorResponse, UpdateAuthorPayload, UpdateAuthorPayload> {
    @Autowired
    public AuthorController(IAuthorService authorService) {
        super(authorService);
    }
}
