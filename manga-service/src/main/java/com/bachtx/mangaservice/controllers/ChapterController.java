package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.mangaservice.entities.Chapter;
import com.bachtx.mangaservice.services.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chapter")
public class ChapterController extends _ACRUDRestController<Chapter, ChapterResponse, CreateChapterPayload, UpdateChapterPayload> {
    @Autowired
    public ChapterController(IChapterService chapterService) {
        super(chapterService);
    }
}
