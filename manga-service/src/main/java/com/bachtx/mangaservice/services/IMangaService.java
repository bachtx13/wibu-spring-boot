package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;

public interface IMangaService extends  IBaseCRUDService<MangaResponse, UpdateMangaPayload, UpdateMangaPayload>{
}
