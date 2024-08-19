package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;

public interface IChapterService extends IBaseCRUDService<ChapterResponse, CreateChapterPayload, UpdateChapterPayload>{}