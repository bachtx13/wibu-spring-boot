package com.bachtx.mangaservice.services;

import com.bachtx.wibucommon.enums.ESortType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface IBaseCRUDService<R, C, U> {
    R create(C payload);
    R update(UUID id, U payload);
    R updateStatus(UUID id, boolean status);
    R getById(UUID id);
    List<R> getAll(Pageable pageable);
    default List<R> getAll(Integer pageNumber, Integer pageSize, String sortBy, ESortType eSortType){
        Sort sort = Sort.by(sortBy);
        if(eSortType == ESortType.ASC){
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return this.getAll(pageable);
    }
}
