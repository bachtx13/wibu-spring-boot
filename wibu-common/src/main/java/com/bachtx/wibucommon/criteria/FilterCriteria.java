package com.bachtx.wibucommon.criteria;

import com.bachtx.wibucommon.enums.EFilterOperation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilterCriteria {
    private String target;
    private Object value;
    private EFilterOperation operation;

    @JsonProperty("target")
    public void deserializeTarget(String target) {
        this.target = target;
    }
    @JsonProperty("value")
    public void deserializeValue(Object value) {
        this.value = value;
    }
    @JsonProperty("operation")
    public void deserializeOperation(String operation) {
        this.operation = EFilterOperation.valueOf(operation.toUpperCase());
    }
}
