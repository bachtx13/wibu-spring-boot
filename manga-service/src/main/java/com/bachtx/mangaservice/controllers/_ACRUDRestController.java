package com.bachtx.mangaservice.controllers;

import com.bachtx.mangaservice.services._IBaseCRUDService;
import com.bachtx.wibucommon.dtos.response.MetaResponse;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.entities.EntityTemplate;
import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.EResponseStatus;
import com.bachtx.wibucommon.enums.ESortType;
import com.bachtx.wibucommon.enums.EStatusAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class _ACRUDRestController<
        Entity extends EntityTemplate,
        Response,
        CreateRequest,
        UpdateRequest
        > {
    private final _IBaseCRUDService<Entity, Response, CreateRequest, UpdateRequest> baseCRUDService;

    protected _ACRUDRestController(_IBaseCRUDService<Entity, Response, CreateRequest, UpdateRequest> baseCRUDService) {
        this.baseCRUDService = baseCRUDService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    protected ResponseTemplate<Response> getById(
            @PathVariable("id") UUID id
    ) {
        return ResponseTemplate.<Response>builder()
                .data(baseCRUDService.getById(id))
                .message("Get data success")
                .status(EResponseStatus.SUCCESS)
                .build();
    }

    @RequestMapping(value = "get-all", method = RequestMethod.GET)
    protected ResponseTemplate<List<Response>> getAll(
            @RequestParam(name = "isTakeTheWhole", defaultValue = "false") boolean isTakeTheWhole,
            @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "lastUpdated") String sortBy,
            @RequestParam(name = "sortType", defaultValue = "desc") ESortType sortType,
            @RequestParam(name = "filterRules", defaultValue = "") String filterRules,
            @RequestParam(name = "status", defaultValue = "enabled") ERecordStatus status
    ) {
        return ResponseTemplate.<List<Response>>builder()
                .meta(
                        MetaResponse.builder()
                                .pageNumber(pageNumber)
                                .pageSize(pageSize)
                                .numberOfRecords(baseCRUDService.getNumberOfRecords(status))
                                .build()
                )
                .data(
                        baseCRUDService.getAll(
                                status,
                                pageNumber,
                                pageSize,
                                sortBy,
                                sortType,
                                filterRules
                        )
                )
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
        boolean isDisable =
                Objects.equals(request.getRequestURI().split("/")[1], EStatusAction.DE_ACTIVE.getAction());
        return this.updateStatus(id, isDisable);
    }

    protected ResponseTemplate<Response> updateStatus(
            UUID id,
            boolean isDisable
    ) {
        return ResponseTemplate.<Response>builder()
                .data(baseCRUDService.updateStatus(id, isDisable))
                .status(EResponseStatus.SUCCESS)
                .build();
    }
}
