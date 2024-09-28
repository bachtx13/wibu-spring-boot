package com.bachtx.mangaservice.services;

import com.bachtx.mangaservice.contexts.AuthenticationContextHolder;
import com.bachtx.mangaservice.contexts.models.AuthenticationContext;
import com.bachtx.mangaservice.specifications.BaseFilterSpecification;
import com.bachtx.mangaservice.specifications.BaseFilterSpecification.FilterCriteria;
import com.bachtx.wibucommon.enums.EFilterOperation;
import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.ESortType;
import com.bachtx.wibucommon.enums.EUserRole;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public interface _IBaseCRUDService<Entity, Response, CreateRequest, UpdateRequest> {
    Response create(CreateRequest payload);

    Response update(UUID id, UpdateRequest payload);

    Response updateStatus(UUID id, boolean isDisable);

    Response getById(UUID id);

    List<Response> getAll(Pageable pageable, Specification<Entity> filterSpecification);

    default List<Response> getAll(
            ERecordStatus status,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            ESortType eSortType,
            String rawFilterRules
    ) {
        Sort sort = Sort.by(sortBy);
        if (eSortType == ESortType.ASC) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        BaseFilterSpecification<Entity> filterSpecification = new BaseFilterSpecification<>();
        if (!rawFilterRules.isEmpty()) {
            rawFilterRules = URLDecoder.decode(rawFilterRules, StandardCharsets.UTF_8);
            Type collectionType = new TypeToken<List<FilterCriteria>>() {
            }.getType();
            try {
                List<FilterCriteria> filterCriteriaList = new Gson().fromJson(rawFilterRules, collectionType);
                filterCriteriaList.forEach(filterSpecification::add);
            } catch (JsonSyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
        status = this.calculateRecordStatusByRole(status);
        if (status != ERecordStatus.IGNORE_STATUS) {
            filterSpecification.add(new FilterCriteria(
                    "disabled",
                    status == ERecordStatus.DISABLED,
                    EFilterOperation.EQUAL
            ));
        }

        return this.getAll(pageable, filterSpecification);
    }

    Long getNumberOfRecords(ERecordStatus status);

    default ERecordStatus calculateRecordStatusByRole(ERecordStatus status) {
        AuthenticationContext authenticationContext = AuthenticationContextHolder.getContext();
        if (status != ERecordStatus.ENABLED && !authenticationContext.hasRole(EUserRole.ROLE_ADMIN)) {
            status = ERecordStatus.ENABLED;
        }
        return status;
    }
}
