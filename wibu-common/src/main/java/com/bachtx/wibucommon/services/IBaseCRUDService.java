package com.bachtx.wibucommon.services;

import com.bachtx.wibucommon.criteria.FilterCriteria;
import com.bachtx.wibucommon.enums.EFilterOperation;
import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.ESortType;
import com.bachtx.wibucommon.specifications.BaseFilterSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public interface IBaseCRUDService<Entity, Response, CreateRequest, UpdateRequest > {
    Response create(CreateRequest payload);
    Response update(UUID id, UpdateRequest payload);
    Response updateStatus(UUID id, boolean isDeActive);
    Response getById(UUID id);
    List<Response> getAll(Pageable pageable, Specification<Entity> filterSpecification);
    default List<Response> getAll(
            boolean isTakeTheWhole,
            ERecordStatus status,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            ESortType eSortType,
            String rawFilterRules
    ){
        Sort sort = Sort.by(sortBy);
        if(eSortType == ESortType.ASC){
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        BaseFilterSpecification<Entity> filterSpecification = new BaseFilterSpecification<>();
        if(!rawFilterRules.isEmpty()){
            try{
                rawFilterRules = URLDecoder.decode(rawFilterRules, StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                List<FilterCriteria> filterCriteriaList = objectMapper.readValue(rawFilterRules, new TypeReference<>() {});
                filterCriteriaList.forEach(filterSpecification::add);
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        status = this.calculateRecordStatusByRole(status);
        if(status != ERecordStatus.IGNORE_STATUS){
            filterSpecification.add(new FilterCriteria(
                    "disabled",
                    status == ERecordStatus.DISABLED,
                    EFilterOperation.EQUAL
            ));
        }

        return this.getAll(pageable, filterSpecification);
    }
    Long getNumberOfRecords(ERecordStatus status);

    ERecordStatus calculateRecordStatusByRole(ERecordStatus status);
}