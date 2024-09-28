package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.entities.Manga;
import com.bachtx.mangaservice.services.IMangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class MangaController extends _ACRUDRestController<Manga, MangaResponse, UpdateMangaPayload, UpdateMangaPayload> {
    @Autowired
    protected MangaController(IMangaService mangaService) {
        super(mangaService);
    }
}
