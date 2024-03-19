package org.bachtx.wibuspringboot.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bachtx.wibuspringboot.enums.EResponseStatus;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<D> {
    private String message;
    private D data;
    private D error;
    private EResponseStatus status;
}
