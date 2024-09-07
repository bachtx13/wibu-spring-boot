package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;
import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.mangaservice.services.IGenreService;
import com.bachtx.wibucommon.controllers.ACRUDRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("genre")
public class GenreController extends ACRUDRestController<Genre, GenreResponse, UpdateGenrePayload, UpdateGenrePayload> {
    @Autowired
    protected GenreController(IGenreService genreService) {
        super(genreService);
    }
}
