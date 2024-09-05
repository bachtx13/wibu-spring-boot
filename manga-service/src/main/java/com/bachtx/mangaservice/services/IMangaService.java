package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.wibucommon.services.IBaseCRUDService;

public interface IMangaService extends IBaseCRUDService<UpdateMangaPayload, UpdateMangaPayload, MangaResponse> {
}
