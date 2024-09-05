package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.wibucommon.services.IBaseCRUDService;

public interface IChapterService extends IBaseCRUDService<CreateChapterPayload, UpdateChapterPayload, ChapterResponse> {}