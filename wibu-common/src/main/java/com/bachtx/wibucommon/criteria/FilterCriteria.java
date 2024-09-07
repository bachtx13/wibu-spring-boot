package com.bachtx.wibucommon.criteria;

import com.bachtx.wibucommon.enums.EFilterOperation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterCriteria {
    private String target;
    private Object value;
    private EFilterOperation operation;

    @JsonCreator
    public FilterCriteria(
            @JsonProperty("target") String target,
            @JsonProperty("value") String value,
            @JsonProperty("operation") String operation) {
        this.target = target;
        this.operation = EFilterOperation.valueOf(operation.toUpperCase());
        this.value = value;
    }
}
