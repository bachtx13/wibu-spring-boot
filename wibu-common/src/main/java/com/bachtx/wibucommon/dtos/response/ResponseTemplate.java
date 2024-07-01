package com.bachtx.wibucommon.dtos.response;

import com.bachtx.wibucommon.enums.EResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTemplate<D> {
    private String message;
    private D data;
    private List<ErrorResponse> errors;
    private EResponseStatus status;
}
