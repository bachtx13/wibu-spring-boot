package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.services.IMangaService;
import com.bachtx.wibucommon.constant.SecurityConstant;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.bachtx.wibucommon.enums.ESortType;
import com.bachtx.wibucommon.enums.EStatusAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MangaController {
    private final IMangaService mangaService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseTemplate<MangaResponse> getById(@PathVariable UUID id) {
        return ResponseTemplate.<MangaResponse>builder()
                .data(mangaService.getById(id))
                .message("Get manga success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    public ResponseTemplate<List<MangaResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastUpdated") String sortBy,
            @RequestParam(defaultValue = "desc") ESortType sortType
    ) {
        return ResponseTemplate.<List<MangaResponse>>builder()
                .data(mangaService.getAll(pageNumber, pageSize, sortBy, sortType))
                .message("Get mangas success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseTemplate<MangaResponse> create(
            @RequestBody @Valid UpdateMangaPayload payload,
            @RequestHeader(value = SecurityConstant.AUTHORIZATION, required = true) String authorization
    ){
        return ResponseTemplate.<MangaResponse>builder()
                .data(mangaService.create(payload, authorization))
                .message("Create manga success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public ResponseTemplate<MangaResponse> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateMangaPayload payload){
        return ResponseTemplate.<MangaResponse>builder()
                .data(mangaService.update(id, payload))
                .message("Update manga success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = {
            "active/{id}",
            "deActive/{id}"
    }, method = RequestMethod.PATCH)
    public ResponseTemplate<MangaResponse> updateStatus(HttpServletRequest request, @PathVariable("id") UUID id){
        boolean action =
                Objects.equals(request.getRequestURI().split("/")[2], EStatusAction.DE_ACTIVE.getAction());
        return ResponseTemplate.<MangaResponse>builder()
                .data(mangaService.updateStatus(id, action))
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
