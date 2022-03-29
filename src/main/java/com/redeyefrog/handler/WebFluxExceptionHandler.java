package com.redeyefrog.handler;

import com.redeyefrog.dto.common.CommonHeader;
import com.redeyefrog.dto.common.CommonRs;
import com.redeyefrog.enums.StatusCode;
import com.redeyefrog.exception.WebFluxRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class WebFluxExceptionHandler {

    @ExceptionHandler({ WebFluxRuntimeException.class })
    public Mono<?> webFluxRuntimeExceptionHandler(WebFluxRuntimeException e, ServerHttpRequest request) {
        log.error("traceId={}", request.getHeaders().getFirst("Trace-Id"));

        log.error("Error Occur: Code={}, Desc={}", e.getCode(), e.getDesc());

        CommonHeader header = e.getHeader();
        header.setReturnCode(e.getCode());
        header.setReturnDesc(e.getDesc());

        CommonRs rs = new CommonRs();
        rs.setHeader(header);

        return Mono.just(rs);
    }

    @ExceptionHandler({ RuntimeException.class })
    public Mono<?> runtimeExceptionHandler(RuntimeException e, ServerHttpRequest request) {
        log.error("Unknown Error Occur: ", e.getMessage(), e);

        CommonHeader header = new CommonHeader();
        header.setReturnCodeAndDesc(StatusCode.UNKNOWN);

        CommonRs rs = new CommonRs();
        rs.setHeader(header);

        return Mono.just(rs);
    }

}
