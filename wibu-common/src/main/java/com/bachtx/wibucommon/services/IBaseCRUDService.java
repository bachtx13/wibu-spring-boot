package com.bachtx.wibucommon.services;

import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.ESortType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface IBaseCRUDService<CreateRequest, UpdateRequest, Response> {
    Response create(CreateRequest payload);
    Response update(UUID id, UpdateRequest payload);
    Response updateStatus(UUID id, boolean isDeActive);
    Response getById(UUID id);
    List<Response> getAll(Pageable pageable);
    default List<Response> getAll(Integer pageNumber, Integer pageSize, String sortBy, ESortType eSortType){
        Sort sort = Sort.by(sortBy);
        if(eSortType == ESortType.ASC){
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return this.getAll(pageable);
    }
    Long getNumberOfRecords(ERecordStatus status);
}
