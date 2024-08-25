package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;
import com.bachtx.mangaservice.services.IAuthorService;
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
@RequestMapping("author")
public class AuthorController {
    private final IAuthorService authorService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseTemplate<AuthorResponse> getById(@PathVariable UUID id) {
        return ResponseTemplate.<AuthorResponse>builder()
                .data(authorService.getById(id))
                .message("Get author success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    public ResponseTemplate<List<AuthorResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastUpdated") String sortBy,
            @RequestParam(defaultValue = "desc") ESortType sortType
            ) {
        return ResponseTemplate.<List<AuthorResponse>>builder()
                .data(authorService.getAll(pageNumber, pageSize, sortBy, sortType))
                .message("Get authors success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseTemplate<AuthorResponse> create(@RequestBody @Valid UpdateAuthorPayload payload){
        return ResponseTemplate.<AuthorResponse>builder()
                .data(authorService.create(payload))
                .message("Create author success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public ResponseTemplate<AuthorResponse> update(@PathVariable("id") UUID id, @RequestBody @Valid UpdateAuthorPayload payload){
        return ResponseTemplate.<AuthorResponse>builder()
                .data(authorService.update(id, payload))
                .message("Update author success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = {
            "active/{id}",
            "deActive/{id}"
    }, method = RequestMethod.PATCH)
    public ResponseTemplate<AuthorResponse> updateStatus(HttpServletRequest request, @PathVariable("id") UUID id){
        boolean action =
                Objects.equals(request.getRequestURI().split("/")[2], EStatusAction.DE_ACTIVE.getAction());
        return ResponseTemplate.<AuthorResponse>builder()
                .data(authorService.updateStatus(id, action))
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
