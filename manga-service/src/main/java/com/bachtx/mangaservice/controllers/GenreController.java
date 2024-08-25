package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;
import com.bachtx.mangaservice.services.IGenreService;
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
@RequestMapping("genre")
public class GenreController {
    private final IGenreService genreService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseTemplate<GenreResponse> getById(@PathVariable UUID id) {
        return ResponseTemplate.<GenreResponse>builder()
                .data(genreService.getById(id))
                .message("Get genre success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    public ResponseTemplate<List<GenreResponse>> getAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastUpdated") String sortBy,
            @RequestParam(defaultValue = "desc") ESortType sortType
    ) {
        return ResponseTemplate.<List<GenreResponse>>builder()
                .data(genreService.getAll(pageNumber, pageSize, sortBy, sortType))
                .message("Get genres success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseTemplate<GenreResponse> create(@RequestBody @Valid UpdateGenrePayload payload){
        return ResponseTemplate.<GenreResponse>builder()
                .data(genreService.create(payload))
                .message("Create genre success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public ResponseTemplate<GenreResponse> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateGenrePayload payload){
        return ResponseTemplate.<GenreResponse>builder()
                .data(genreService.update(id, payload))
                .message("Update genre success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = {
            "active/{id}",
            "deActive/{id}"
    }, method = RequestMethod.PATCH)
    public ResponseTemplate<GenreResponse> updateStatus(HttpServletRequest request, @PathVariable("id") UUID id){
        boolean action =
                Objects.equals(request.getRequestURI().split("/")[2], EStatusAction.DE_ACTIVE.getAction());
        return ResponseTemplate.<GenreResponse>builder()
                .data(genreService.updateStatus(id, action))
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
