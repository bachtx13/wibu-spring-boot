package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.mangaservice.entities.Chapter;

public interface IChapterService extends _IBaseCRUDService<Chapter, ChapterResponse, CreateChapterPayload, UpdateChapterPayload> {
}