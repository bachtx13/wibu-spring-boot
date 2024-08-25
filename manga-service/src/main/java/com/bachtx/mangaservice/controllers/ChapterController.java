package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.mangaservice.services.IChapterService;
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
@RequestMapping("chapter")
public class ChapterController {
    private final IChapterService chapterService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseTemplate<ChapterResponse> getById(@PathVariable UUID id) {
        return ResponseTemplate.<ChapterResponse>builder()
                .data(chapterService.getById(id))
                .message("Get chapter success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    public ResponseTemplate<List<ChapterResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastUpdated") String sortBy,
            @RequestParam(defaultValue = "desc") ESortType sortType

    ) {
        return ResponseTemplate.<List<ChapterResponse>>builder()
                .data(chapterService.getAll(pageNumber, pageSize, sortBy, sortType))
                .message("Get chapters success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseTemplate<ChapterResponse> create(
            @RequestBody @Valid CreateChapterPayload payload,
            @RequestHeader(value = SecurityConstant.AUTHORIZATION, required = true) String authorization
    ){
        return ResponseTemplate.<ChapterResponse>builder()
                .data(chapterService.create(payload))
                .message("Create chapter success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public ResponseTemplate<ChapterResponse> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateChapterPayload payload){
        return ResponseTemplate.<ChapterResponse>builder()
                .data(chapterService.update(id, payload))
                .message("Update chapter success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = {
            "active/{id}",
            "deActive/{id}"
    }, method = RequestMethod.PATCH)
    public ResponseTemplate<ChapterResponse> updateStatus(HttpServletRequest request, @PathVariable("id") UUID id){
        boolean action =
                Objects.equals(request.getRequestURI().split("/")[2], EStatusAction.DE_ACTIVE.getAction());
        return ResponseTemplate.<ChapterResponse>builder()
                .data(chapterService.updateStatus(id, action))
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
