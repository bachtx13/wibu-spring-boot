package com.bachtx.wibucommon.controllers;

import com.bachtx.wibucommon.dtos.response.MetaResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.entities.EntityTemplate;
import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.bachtx.wibucommon.enums.ESortType;
import com.bachtx.wibucommon.enums.EStatusAction;
import com.bachtx.wibucommon.services.IBaseCRUDService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class ACRUDRestController<
        Entity extends EntityTemplate,
        Response,
        CreateRequest,
        UpdateRequest
        > {
    private final IBaseCRUDService<Entity, Response, CreateRequest, UpdateRequest> baseCRUDService;

    protected ACRUDRestController(IBaseCRUDService<Entity, Response, CreateRequest, UpdateRequest> baseCRUDService) {
        this.baseCRUDService = baseCRUDService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    protected ResponseTemplate<Response> getById(
            @PathVariable UUID id
    ) {
        return ResponseTemplate.<Response>builder()
                .data(baseCRUDService.getById(id))
                .message("Get data success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    protected ResponseTemplate<List<Response>> getAll(
            @RequestParam(defaultValue = "false") boolean isTakeTheWhole,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastUpdated") String sortBy,
            @RequestParam(defaultValue = "desc") ESortType sortType,
            @RequestParam(defaultValue = "") String filterRules,
            @RequestParam(defaultValue = "enabled") ERecordStatus status
    ) {
        return ResponseTemplate.<List<Response>>builder()
                .meta(
                        MetaResponse.builder()
                                .pageNumber(pageNumber)
                                .pageSize(pageSize)
                                .numberOfRecords(baseCRUDService.getNumberOfRecords(status))
                                .build()
                )
                .data(baseCRUDService.getAll(isTakeTheWhole, pageNumber, pageSize, sortBy, sortType, filterRules))
                .message("Get data success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    protected ResponseTemplate<Response> create(
            @RequestBody @Valid CreateRequest payload
    ) {
        return ResponseTemplate.<Response>builder()
                .data(baseCRUDService.create(payload))
                .message("Create success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    protected ResponseTemplate<Response> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid UpdateRequest payload
    ) {
        return ResponseTemplate.<Response>builder()
                .data(baseCRUDService.update(id, payload))
                .message("Update success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = {"active/{id}", "deActive/{id}"}, method = RequestMethod.PATCH)
    protected ResponseTemplate<Response> updateStatus(
            HttpServletRequest request,
            @PathVariable("id") UUID id
    ) {
        boolean isDeActive =
                Objects.equals(request.getRequestURI().split("/")[2], EStatusAction.ACTIVE.getAction());
        return this.updateStatus(id, isDeActive);
    }

    protected ResponseTemplate<Response> updateStatus(
            UUID id,
            boolean isDeActive
    ) {
        return ResponseTemplate.<Response>builder()
                .data(baseCRUDService.updateStatus(id, isDeActive))
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
