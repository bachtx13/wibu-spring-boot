package com.bachtx.wibucommon.dtos.response;

import com.bachtx.wibucommon.enums.ESortType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaResponse {
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private ESortType sortType;
    private Long numberOfRecords;
}
