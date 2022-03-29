package com.redeyefrog.exception;

import com.redeyefrog.dto.common.CommonHeader;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebFluxRuntimeException extends RuntimeException {

    private Throwable throwable;

    private String code;

    private String desc;

    private CommonHeader header;

}
